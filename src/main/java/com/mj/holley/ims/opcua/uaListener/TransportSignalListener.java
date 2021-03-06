package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.repository.TransportTaskRepository;
import com.mj.holley.ims.service.WmsSubmitService;
import com.mj.holley.ims.service.WmsTaskRequestService;
import com.mj.holley.ims.service.dto.WmsResultsReportedDTO;
import com.mj.holley.ims.service.dto.WmsTaskRequestDTO;
import com.mj.holley.ims.service.dto.resultReportedDTO;
import com.mj.holley.ims.service.dto.taskRequestDTO;
import com.mj.holley.ims.service.util.ConstantValue;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Wanghui on 2018/4/3.
 */
@Slf4j
@Component
public class TransportSignalListener implements MonitoredDataItemListener {

    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

    @Inject
    private TransportTaskRepository transportTaskRepository;

    @Inject
    private WmsTaskRequestService wmsTaskRequestService;

    @Inject
    private WmsSubmitService wmsSubmitService;

    @Override
    public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValuePre, DataValue dataValueNew) {


        log.info(monitoredDataItem + "111111");
        log.info(dataValueNew.getValue().intValue() + " ---222222");
        log.info(dataValuePre.getValue().intValue() + " +++3333333");
        if (dataValueNew.getValue().intValue() == 0) {
            return;
        }
        if (dataValueNew.getValue().intValue() == 1) {          //订阅到读码信号
            try {
                String barCodeAddress = monitoredDataItem.toString().replace("signal", "code"); //将每个工位的读码信号地址、读码地址规则化 eg:****.工位.signal; ****.工位.code;
                String barcode = opcUaClientTemplate.readNodeVariant(new NodeId(2, barCodeAddress)).getValue().toString(); //读对应工位条码

                handleBarcode(barCodeAddress, barcode);

            } catch (OpcUaClientException e) {
                log.error("TransportLine  about | opcua error when read or write value {}", e);

            }
        }
    }

    /**
     * 物流线读取周转箱条码后进行处理
     *
     * @param barcodeAddress
     * @param barcode
     */
    private void handleBarcode(String barcodeAddress, String barcode) {
        // TODO: 2018/4/3 根据条码规则对条码校验符合规则进行处理
        String posId = barcodeAddress.substring(barcodeAddress.indexOf("****") + 6, barcodeAddress.indexOf("code"));//****.工位.point 截取工位
        Optional<TransportTask> transportTaskOptional = transportTaskRepository.findFirstBylPNAndCompletionTimeIsNullOrderByIdDesc(barcode);
        String toPos;
        if(ConstantValue.TRANSPORT_START_POINT_LIST.contains(posId)){      //读码点为任务交接点时下发PLC该任务目标位置
            if(transportTaskOptional.isPresent()){
                toPos = transportTaskOptional.get().getToPos();
            }else {
                toPos = receiveTaskFromWms(barcode);
            }
            try {
                opcUaClientTemplate.writeNodeValue(new NodeId(2, barcodeAddress.replace("code", "signal")), 0);
                opcUaClientTemplate.writeNodeValue(new NodeId(2, barcodeAddress.replace("code", "pos")), toPos);
            } catch (OpcUaClientException e) {
                log.error("opc ua exception when write brineCheck model" + e.getMessage());
            }
        }else{                //读码点为任务结束时的下货点
            // TODO: 2018/4/18 上传WMS任务结束；更新本地对应任务为结束状态：正常结束、异常结束。结束时间等
            submitTaskEndToWms(transportTaskOptional);
        }


    }

    /**
     * 本地无任务时主动请求WMS周转箱对应的任务
     * @param barcode
     * @return 物流任务对应的目标位置
     */
    private String receiveTaskFromWms(String barcode){
        // TODO: 2018/4/3 对接WMS接口返回任务，保存本地数据库。
        taskRequestDTO taskRequest = new taskRequestDTO(null,barcode,"","","","","","");
        List<taskRequestDTO> list = new ArrayList<>();
        list.add(taskRequest);
        WmsTaskRequestDTO dto = new WmsTaskRequestDTO("","","", list);
        HashMap hashMap = null;
        try {
            hashMap = wmsTaskRequestService.taskRequest(dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransportTask transportTask = new TransportTask()
            .taskType(hashMap.get("TASK_TYPE").toString())
            .taskPrty(hashMap.get("TASK_PRTY").toString())
            .taskFlag(hashMap.get("TASK_FLAG").toString())
            .lPN(hashMap.get("LPN").toString())
            .frmPos(hashMap.get("FRM_POS").toString())
            .frmPosType(hashMap.get("FRM_POS_TYPE").toString())
            .toPos(hashMap.get("TO_POS").toString())
            .toPosType(hashMap.get("TO_POS_TYPE").toString())
            .remark(hashMap.get("DEC").toString())
            .storeType(hashMap.get("STORE_TYPE").toString())
            .taskID(Long.parseLong(hashMap.get("TASK_ID").toString()));
        transportTaskRepository .saveAndFlush(transportTask);
        return hashMap.get("TO_POS").toString();
    }


    private void submitTaskEndToWms(Optional<TransportTask> transportTaskOptional){
        // TODO: 2018/4/3 对接WMS接口文档第三个接口上传WMS任务完成 并且修改本地存储的对应任务的状态及完成时间
        resultReportedDTO resultReported = new resultReportedDTO(transportTaskOptional.get().getSerialID(),transportTaskOptional.get().getTaskID(),
            transportTaskOptional.get().getTaskFlag(),transportTaskOptional.get().getlPN(),"","","",transportTaskOptional.get().getRemark());
        List<resultReportedDTO> list = new ArrayList<>();
        list.add(resultReported);
        WmsResultsReportedDTO dto = new WmsResultsReportedDTO(transportTaskOptional.get().getFunID(),"","", list);
        try {
            wmsSubmitService.submitTaskExecutionResult(dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        transportTaskRepository.updateTransportTaskByTaskId(transportTaskOptional.get().getCompletionTime() , transportTaskOptional.get().getTaskFlag() , transportTaskOptional.get().getTaskID());
    }
}
