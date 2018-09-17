package com.mj.holley.ims.opcua;

import com.mj.holley.ims.config.opcua.OpcUaProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by SunYi on 2016/3/7/0007.
 */
@Data
public class OpcUaSubscribeNodes {
    // TODO: [All] add subscribe node list here
//    private List<String> employeeSubscribeNodes;
//    private List<String> branchSelectSubscribeNodes;
//    private List<String> cartonSpaceSubscribeNodes;
//    private List<String> fridgeListSubscribeNodes;
//    private List<String> printerSubscribeNodes;
//    private List<String> cartonBarcodeSubscribeNodes;
//    private List<String> warningSubscribeNodes;
//    private List<String> printerExceptionSubscribeNodes;
    private List<String> scanSignalSubscribeNodes;
//    private List<String> scanSignalSubscribeNodes9;
    private List<String> transportSignalSubscribeNodes;

    private List<String> getRealSubscribeNodesList(List<String> opcUaPropertiesList) {
        if (opcUaPropertiesList != null && opcUaPropertiesList.size() != 0 && opcUaPropertiesList.get(0).contains("NodeBase")) {
            String[] machineNoList = new String[0];
            String[] list = opcUaPropertiesList.get(0).split("-");
            String nodeBase = list[0].trim().substring(list[0].trim().indexOf("#") + 1).trim();
            if (opcUaPropertiesList.get(0).contains("No#")) {
                machineNoList = list[1].trim().substring(list[1].trim().indexOf("#") + 1).split(",");
            }
            String[] subVarList = list[list.length - 1].trim().substring(list[list.length - 1].trim().indexOf("#") + 1).split(",");
            opcUaPropertiesList.remove(0);
            if (!(machineNoList.length == 0)) {
                for (String machineNo : machineNoList) {
                    for (String subvar : subVarList) {
                        opcUaPropertiesList.add(nodeBase + machineNo.trim() + "." + subvar.trim());
                    }
                }
            } else {
                for (String subvar : subVarList) {
                    opcUaPropertiesList.add(nodeBase + "." + subvar.trim());
                }
            }
        }
        return opcUaPropertiesList;
    }

    public OpcUaSubscribeNodes(OpcUaProperties opcUaProperties) {
//        this.employeeSubscribeNodes = opcUaProperties.getEmployeeSubscribeNodes();
//        this.printerSubscribeNodes = opcUaProperties.getPrinterSubscribeNodes();
//        this.branchSelectSubscribeNodes = opcUaProperties.getBranchSelectSubscribeNodes();
        this.scanSignalSubscribeNodes = getRealSubscribeNodesList(opcUaProperties.getScanSignalSubscribeNodes());
//        this.scanSignalSubscribeNodes9 = getRealSubscribeNodesList(opcUaProperties.getScanSignalSubscribeNodes9());
        this.transportSignalSubscribeNodes = getRealSubscribeNodesList(opcUaProperties.getTransportSignalSubscribeNodes());
//        this.fridgeListSubscribeNodes = opcUaProperties.getFridgeListSubscribeNodes();
//        this.cartonBarcodeSubscribeNodes = opcUaProperties.getCartonBarcodeSubscribeNodes();
//        this.warningSubscribeNodes = opcUaProperties.getWarningSubscribeNodes();
//        this.printerExceptionSubscribeNodes = opcUaProperties.getPrinterExceptionSubscribeNodes();
//        this.cartonQuantitySubscribeNodes = opcUaProperties.getCartonQuantitySubscribeNodes();/////
    }
}
