package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.service.MesSubmitService;
import com.mj.holley.ims.service.RedisService;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Wanghui on 2017/5/20.
 */

@Slf4j
@Component
public class ScanSignalListener implements MonitoredDataItemListener {

    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

    @Inject
    private MesSubmitService mesSubmitService;

    @Inject
    private RedisService redisService;

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

                handleBarcode(barCodeAddress,barcode);

            } catch (OpcUaClientException e) {
                log.error("Labeling machine about | opcua error when read or write value {}", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleBarcode(String barcodeAddress,String barCode) throws IOException {
        // TODO: 2018/3/26 根据条码规则对条码校验符合规则进行处理
        String stationId = barcodeAddress.substring(barcodeAddress.indexOf("****") + 6, barcodeAddress.indexOf("code"));//****.工位.code 截取工位
        ScanningResgistrationDTO dto = new ScanningResgistrationDTO( Integer.parseInt(redisService.readAndInc("processPk").toString()),
            barCode,stationId,"1","","OK","");
        if (mesSubmitService.submitScanningRegistration(dto).get("resultValue").toString().contains("-1")){  //MES 接口返回-1则该产品存在缺陷
            boolean isFault = Boolean.TRUE;
        }

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
