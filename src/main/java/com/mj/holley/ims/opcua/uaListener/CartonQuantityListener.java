package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Wanghui on 2017/5/20.
 */

@Slf4j
@Component
public class CartonQuantityListener implements MonitoredDataItemListener {

    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

    @Override
    public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValuePre, DataValue dataValueNew) {


        log.info(monitoredDataItem + "111111");
        log.info(dataValueNew.getValue().intValue() + " ---222222");
        log.info(dataValuePre.getValue().intValue() + " +++3333333");
//        if (dataValueNew.getValue().intValue() == 0) {
//            return;
//        }
//        if (dataValueNew.getValue().intValue() == 1) {
//            // TODO 测试
//            //  获取仓位号   ns=2;s=CartonSpace.CartonSpace_A1.barcode -> CartonSpace_A1
//            String line = OpcUaUtil.getObject(monitoredDataItem.getNodeId());
//            //  根据包装线区分redis的key,读取redis仓位信息，取纸箱数量减1后写回，并推送一体机
//            int flag = Integer.parseInt(line.substring(line.length()-1));
//            if (line.contains("A")) {
//                updateCartonQuantity(PackingBoxService.A_PACKING_BOX_KEY, flag);
//            } else {
//                updateCartonQuantity(PackingBoxService.B_PACKING_BOX_KEY, flag);
//            }
//        }
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
