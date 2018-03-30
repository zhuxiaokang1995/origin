package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Sn;
import com.mj.holley.ims.domain.Steps;
import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.repository.OrderInfoRepository;
import com.mj.holley.ims.repository.ProcessControlRepository;
import com.mj.holley.ims.repository.SnRepository;
import com.mj.holley.ims.repository.StepsRepository;
import com.mj.holley.ims.service.BindingService;
import com.mj.holley.ims.service.MesSubmitService;
import com.mj.holley.ims.service.RedisService;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import com.mj.holley.ims.service.util.ConstantValue;
import com.mj.holley.ims.service.util.RedisKey;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
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
                log.error("Labeling machine about | opcua error when read or write value {}", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String stationId = barcodeAddress.substring(barcodeAddress.indexOf("****") + 6, barcodeAddress.indexOf("code"));//****.工位.code 截取工位
        boolean isFault = Boolean.FALSE;             //是否存在缺陷
        boolean havingStation = Boolean.FALSE;       //是否有工艺流程
        boolean writeToPlc;
        ScanningResgistrationDTO dto = new ScanningResgistrationDTO(Integer.parseInt(redisService.readAndInc(RedisKey.PROCESS_PK).toString()),
            barCode, stationId, "1", "", "OK", "");
        if (mesSubmitService.submitScanningRegistration(dto).get("resultValue").toString().contains("-1")) {  //MES 接口返回-1则该产品存在缺陷
            isFault = Boolean.TRUE;
        }
        Optional<Sn> snOptional = snRepository.findFirstByHutIDAndIsBindingTrueOrderByIdDesc(barCode);
        if (snOptional.isPresent()) {
            Sn sn = snOptional.get();
            if (redisService.hasKey(sn.getOrderID())) {                                           //redis中是否存在该订单的工艺流程
                if (redisService.readList(sn.getOrderID()).contains(stationId)) {                 //存在则从redis中取该订单的工艺流程
                    havingStation = Boolean.TRUE;
                }
            } else {
                OrderInfo orderInfo = orderInfoRepository.findOneByOrderID(sn.getOrderID()).get(); //redis中不存在则从数据库中取工艺流程并存redis
                List<Steps> stepsList = stepsRepository.findByOrderInfo(orderInfo);
                if (saveRedisStepsByOrder(sn.getOrderID(), stepsList).contains(stationId)) {
                    havingStation = Boolean.TRUE;
                }
            }
            /**
             * 暂时将工位重复判定功能交PLC处理
             */
//            if (havingStation){                                                                      //该工位有工艺，再结合是否是重复工位及重复工位是否入过站
//                havingStation = havingStation && (!isProcessRepeatStation(sn.getSerialNumber(),stationId));
//            }
        }
        if (isFault) {
            writeToPlc = isFault && ConstantValue.REPAIRED_STATION_LIST.contains(stationId);
        } else {
            writeToPlc = havingStation;
        }
        try {
            opcUaClientTemplate.writeNodeValue(new NodeId(2, barcodeAddress.replace("code", "signal")), 0);
            opcUaClientTemplate.writeNodeValue(new NodeId(2, barcodeAddress.replace("code", "run")), writeToPlc);
        } catch (OpcUaClientException e) {
            log.error("opc ua exception when write brineCheck model" + e.getMessage());
        }
        if (writeToPlc) bindingService.saveProcessControlInfo(snOptional,dto);
    }

    /**
     * 保存订单对应工艺流程并设置过期时间48h返回工位编号list
     * @param orderId
     * @param stepsList
     * @return
     */
    private List saveRedisStepsByOrder(String orderId, List<Steps> stepsList) {
        List<String> easyStepsList = stepsList.stream()
            .filter(steps -> steps.getStepAttrID() == "1")       //过滤StepAttrID为1的才是有效的
            .map(steps -> steps.getStationID())
            .collect(Collectors.toList());
        redisService.saveList(orderId, easyStepsList);
        redisService.expireKey(orderId, 48L, TimeUnit.HOURS);
        return easyStepsList;
    }

    /**
     *判断当前工位是否是重复工位，是否需要进去
     * @param stationId
     * @return
     */
    private Boolean isProcessRepeatStation(String stationId,String serialNumber){
        if (ConstantValue.REPEAT_STATION_LIST.contains(stationId)){
            if (processControlRepository.findOneBySerialNumberAndStationID(serialNumber,stationId).isPresent()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
//    private void updateCartonQuantity(String key, int flag) {
//        List<PackingBoxDTO> packingBoxDTOList = (List<PackingBoxDTO>) redisService.readList(key);
//        PackingBoxDTO packingBoxDTO = packingBoxDTOList.get(flag - 1);
//        packingBoxDTO.setStockBalance((packingBoxDTO.getStockBalance() - 1));
//        packingBoxDTOList.set(flag - 1, packingBoxDTO);
//        redisService.saveList(key, packingBoxDTOList);
//        // 推送一体机
//        messagePushService.pushCartonQuantity(packingBoxDTOList);
//    }

//    public static void main(String[] args) {
//        String line ="CartonSpace_A1";
//        System.out.println(Integer.parseInt(line.substring(line.length()-1)));
//}
}
