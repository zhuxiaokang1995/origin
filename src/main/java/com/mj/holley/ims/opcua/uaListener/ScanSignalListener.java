package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.domain.*;
import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.repository.*;
import com.mj.holley.ims.service.BindingService;
import com.mj.holley.ims.service.MesSubmitService;
import com.mj.holley.ims.service.RedisService;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import com.mj.holley.ims.service.util.ConstantValue;
import com.mj.holley.ims.service.util.RedisKey;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Wanghui on 2017/5/20.
 */

@Slf4j
@Component
public class ScanSignalListener implements MonitoredDataItemListener {

    @Inject
    private BindingService bindingService;

    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

    @Inject
    private MesSubmitService mesSubmitService;

    @Inject
    private RedisService redisService;

    @Inject
    private SnRepository snRepository;

    @Inject
    private OrderInfoRepository orderInfoRepository;

    @Inject
    private StepsRepository stepsRepository;

    @Inject
    private ProcessControlRepository processControlRepository;

    @Inject
    private RepeatProcessRepository repeatProcessRepository;

    @Inject
    private AbnormalInformationRepository abnormalInformationRepository;

    @Override
    public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValuePre, DataValue dataValueNew) {

        if (dataValueNew.getValue().intValue() <= 0) {
            return;
        }

        try {
            NodeId nodeId = monitoredDataItem.getNodeId();
            String var = nodeId.toString().substring(nodeId.toString().lastIndexOf("=") + 1).trim();
            String barCodeAddress = var.replace("Signal", "Code"); //将每个工位的读码信号地址、读码地址规则化 eg:****.工位.signal; ****.工位.code;
            String barcode = opcUaClientTemplate.readNodeVariant(new NodeId(6, barCodeAddress)).getValue().toString(); //读对应工位条码
            if (dataValueNew.getValue().intValue() == 32767) {          //订阅到读码信号
                handleBarcode(barCodeAddress, barcode);
            }else {                                                    //订阅到重复工位分配的工位结果
                receiveRepeatStationResult(dataValueNew.getValue().intValue(),barcode);
                String rAddress = var.replace("Signal", "Result");
                opcUaClientTemplate.writeNodeValue(new NodeId(6, rAddress), -3);      //处理完结果之后写-3告诉PLC读取完毕
            }

        } catch (OpcUaClientException e) {
            log.error("Labeling machine about | opcua error when read or write value {}", e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理工艺流程写PLC是否放行
     * @param barcodeAddress
     * @param barCode
     * @throws IOException
     */
    private void handleBarcode(String barcodeAddress, String barCode) throws IOException {
        // TODO: 2018/3/26 根据条码规则对条码校验符合规则进行处理
        String stationId = barcodeAddress.substring(barcodeAddress.indexOf("OPC.") + 4, barcodeAddress.indexOf("-Code")).toLowerCase();//****.工位.code 截取工位
        boolean isFault = Boolean.FALSE;             //是否存在缺陷
        boolean havingStation = Boolean.FALSE;       //是否有工艺流程
        boolean writeToPlc;

        Optional<Sn> snOptional = snRepository.findFirstByHutIDAndIsBindingTrueOrderByIdDesc(barCode);
        if (snOptional.isPresent()) {
            Sn sn = snOptional.get();
            ScanningResgistrationDTO dto = new ScanningResgistrationDTO(Integer.parseInt(redisService.readAndInc(RedisKey.PROCESS_PK).toString()),
                sn.getSerialNumber(), stationId, "1", "", "OK", "");
            if (mesSubmitService.submitScanningRegistration(dto).get("resultValue").toString().contains("false")) {  //MES 接口返回-1则该产品存在缺陷
                isFault = Boolean.TRUE;
            }
            Optional<OrderInfo> orderInfoOptional = orderInfoRepository.findOneByOrderID(sn.getOrderID());
            if (orderInfoOptional.isPresent()){
                OrderInfo orderInfo = orderInfoOptional.get();                              //redis中不存在则从数据库中取工艺流程并存redis
                if (redisService.hasKey(sn.getOrderID())) {                               //redis中是否存在该订单的工艺流程
                    if (redisService.readList(sn.getOrderID()).contains(stationId)) {     //存在则从redis中取该订单的工艺流程
                        havingStation = Boolean.TRUE;
                    }
                } else {
                    List<Steps> stepsList = stepsRepository.findByOrderInfo(orderInfo);
                    if (saveRedisStepsByOrder(sn.getOrderID(), stepsList).contains(stationId)) {
                        havingStation = Boolean.TRUE;
                    }
                }

                if(stationId.equals("q13zp01-zp01")){             //在第一个重复工位的地方写工艺路径
                    Optional <RepeatProcess> repeatProcess = repeatProcessRepository.findByOrderInfo(orderInfo);
                    if(repeatProcess.isPresent()){
                        writeStringToInt("S71500ET200MP station_1.AALinesMJ01.OPC.Q13ZP01-StaGrp.ProType1",repeatProcess.get().getProcessNum());      //写的地址视现场情况而定8个地址最好尾数以数字结束或者直接写字符串降低写的次数
                    }
                }
            }

            /**
             * 暂时将工位重复判定功能交PLC处理
             */
//            if (havingStation){                                                                      //该工位有工艺，再结合是否是重复工位及重复工位是否入过站
//                havingStation = havingStation && (!isProcessRepeatStation(sn.getSerialNumber(),stationId));
//            }
        }else {
            AbnormalInformation abnormalInformation = new AbnormalInformation()
                .lineStationId(stationId)
                .abnormalCause("条码未绑定")
                .abnormalTime(ZonedDateTime.now())
                .remark("");
            abnormalInformationRepository.save(abnormalInformation);
        }
        if (isFault) {
            writeToPlc = isFault && ConstantValue.REPAIRED_STATION_LIST.contains(stationId);
        } else {
            writeToPlc = havingStation;
        }
        try {
//            opcUaClientTemplate.writeNodeValue(new NodeId(6, barcodeAddress.replace("Code", "Signal")), 0);
//            opcUaClientTemplate.writeNodeValue(new NodeId(6, barcodeAddress), 0);
            opcUaClientTemplate.writeNodeValue(new NodeId(6, barcodeAddress.replace("Code", "Result")), (writeToPlc)?-2:-1);//写何值停、放行视现场情况定
        } catch (OpcUaClientException e) {
            log.error("opc ua exception when write brineCheck model" + e.getMessage());
        }
        if (writeToPlc && (!ConstantValue.REPEAT_STATION_NINE_LIST.contains(stationId))) bindingService.saveProcessControlInfo(snOptional,stationId);//重复工位的入站记录由PLC给
    }

    /**
     * 保存订单对应工艺流程并设置过期时间48h返回工位编号list
     * @param orderId
     * @param stepsList
     * @return
     */
    private List saveRedisStepsByOrder(String orderId, List<Steps> stepsList) {
        List<String> easyStepsList = stepsList.stream()
            .filter(steps -> steps.getStepAttrID().equals("true"))       //过滤StepAttrID为1的才是有效的
            .map(steps -> steps.getStationID())
            .collect(Collectors.toList());
        redisService.saveList(orderId, easyStepsList);
        redisService.expireKey(orderId, 48L, TimeUnit.HOURS);
        return easyStepsList;
    }

    /**
     * PLC 分配完产品进入的工位之后会告诉中控系统，但此时的具体进站时间是不准的
     * @param resultValue
     * @param barCode
     */
    private void receiveRepeatStationResult(Integer resultValue,String barCode){
        Optional<Sn> snOptional = snRepository.findFirstByHutIDAndIsBindingTrueOrderByIdDesc(barCode);
        if (snOptional.isPresent()){
            Sn sn = snOptional.get();
            String stationId = "q13zp01-zp0" + String.valueOf(resultValue+1);  //视线体具体工位的规律拼接stationId
            ProcessControl processControl = new ProcessControl()
                .serialNumber(sn.getSerialNumber())
                .hutID(sn.getHutID())
                .orderID(sn.getOrderID())
                .stationID(stationId)
                .result("repeat station")
                .mountGuardTime(ZonedDateTime.now());
            processControlRepository.save(processControl);
        }
    }

    /**
     *判断当前工位是否是重复工位，是否需要进去
     * @param stationId
     * @return
     */
    private Boolean isProcessRepeatStation(String stationId,String serialNumber){
        if (ConstantValue.REPEAT_STATION_NINE_LIST.contains(stationId)){
            if (processControlRepository.findOneBySerialNumberAndStationID(serialNumber,stationId).isPresent()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 将String类型的值分解成Int写到对应OPCUA地址
     * @param StartAddress
     * @param StrValue
     */
    private void writeStringToInt(String StartAddress,String StrValue){
        String subAddress = StartAddress.substring(0,StartAddress.length()-1);
        if(StrValue.length()==8){
            for(int i=0;i<StrValue.length();i++){
                try {
                    opcUaClientTemplate.writeNodeValue(new NodeId(6, subAddress +(i+1)), Integer.valueOf(String.valueOf(StrValue.charAt(i))));
                } catch (OpcUaClientException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
