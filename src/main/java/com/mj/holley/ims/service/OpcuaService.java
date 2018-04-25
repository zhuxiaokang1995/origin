package com.mj.holley.ims.service;


import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.opcua.uaListener.UaConnectionListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Wanghui on 2017/5/20.
 */
@Slf4j
@Service
public class OpcuaService {

    @Inject
    private UaConnectionListener uaConnectionListener;


    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

//    @Inject
//    private MaterialWarehouseRepository materialWarehouseRepository;
//
////    @Inject
////    private UaConnectionListener uaConnectionListener;
//
//
    @PostConstruct
    public void opcuaClientConnect() {
        opcUaClientTemplate.addConnectionListener(uaConnectionListener);
        opcUaClientTemplate.connectAlwaysInBackend();
    }

//    //亮绿灯
//    public void writeGreenData (List<String> data){
//        String greenArea = "S7-1200 station_1.PLC_2.state."+"qinzaiqu"+"Green";
//        for (int i = 0; i < data.size(); i++) {
//            MaterialWarehouse materialWarehouse = materialWarehouseRepository.findByPositionCode(data.get(i));
//            String g = "S7-1200 station_1.PLC_2.Area3."+ materialWarehouse.getPlcAddressGreen();
//            try {
//                opcUaClientTemplate.writeNodeValue(new NodeId(6, g), true);
//            } catch (OpcUaClientException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            opcUaClientTemplate.writeNodeValue(new NodeId(6, greenArea), true);
//        } catch (OpcUaClientException e) {
//            e.printStackTrace();
//        }
////        return  "the green light...";
//    }
//
//
//    //亮红灯
//    public void writeRedData (List<String> data){
//        String redArea = "S7-1200 station_1.PLC_2.state."+"qinzaiqu"+"Red";
//        for (int i = 0; i < data.size(); i++) {
//            MaterialWarehouse materialWarehouse = materialWarehouseRepository.findByPositionCode(data.get(i));
//            String r = "S7-1200 station_1.PLC_2.Area3."+ materialWarehouse.getPlcAddress();
//            try {
//                opcUaClientTemplate.writeNodeValue(new NodeId(6, r), true);
//            } catch (OpcUaClientException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            opcUaClientTemplate.writeNodeValue(new NodeId(6, redArea), true);
//        } catch (OpcUaClientException e) {
//            e.printStackTrace();
//        }
////        return  "the red light...";
//    }
//
//
//    public String readEnergyData(List<String> positionCode) {
//        String result ="";
//        String greenArea = "S7-1200 station_1.PLC_2.state."+"qinzaiqu"+"Green";
//        boolean greenAreaState = false;
//        try {
//            greenAreaState = opcUaClientTemplate.readNodeVariant(new NodeId(6, greenArea)).booleanValue();
//        } catch (OpcUaClientException e) {
//            e.printStackTrace();
//        }
//
//        if (greenAreaState){
//            String redArea = "S7-1200 station_1.PLC_2.state."+"qinzaiqu"+"Red";
//            boolean redAreaState = false;
//            try {
//                redAreaState = opcUaClientTemplate.readNodeVariant(new NodeId(6, redArea)).booleanValue();
//            } catch (OpcUaClientException e) {
//                e.printStackTrace();
//            }
//            if (redAreaState){
//                result = "please wait ...";
//            }else {
//                //亮红灯
//                writeRedData(positionCode);
//                result = "the red light...";
//            }
//        }else {
//            //亮绿灯
//            writeGreenData(positionCode);
//            result = "the green light...";
//        }
//        return result;
//    }
//

    private String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }

    }
}
