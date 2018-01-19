package com.mj.holley.ims.opcua.util;

import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liumin
 */
@Slf4j
public class OpcUaUtil {

    private static Map<String, Object> LAST_NODE_VALUE_MAP = new ConcurrentHashMap<>();

    public static boolean isNewNodeValueValid(NodeId id,
                                              DataValue oldDataValue,
                                              DataValue newDataValue) {
        String nodeId = id.toString();
        if (null == oldDataValue) {
            log.info("The subscription for {} is initialized.", nodeId);
            LAST_NODE_VALUE_MAP.put(nodeId, newDataValue.getValue().getValue());
            return false;
        }

        Variant oldVariant = oldDataValue.getValue();
        Variant newVariant = newDataValue.getValue();

        if (newVariant.getValue().equals(oldVariant.getValue())
            || (LAST_NODE_VALUE_MAP.get(nodeId) != null
            && LAST_NODE_VALUE_MAP.get(nodeId).equals(newVariant.getValue()))) {

            log.debug("--->>" + nodeId + " from " + oldVariant + " to " + newVariant + "<<---");
            log.error("data change error: listener YOU DU!!!");
            return false;
        }

        log.info("--->>" + nodeId + " from " + oldVariant + " to " + newVariant + "<<--- go.");
        LAST_NODE_VALUE_MAP.put(nodeId, newVariant.getValue());
        return true;
    }

    // 获得节点变量名  ns=2;s=EmployeeManagement.Monitor.creditCardNO  -> creditCardNO
    public static String getNodeVar(NodeId nodeId) {
        return nodeId.toString().substring(nodeId.toString().lastIndexOf(".") + 1).trim();
    }

    //  获得节点名   ns=2;s=EmployeeManagement.Monitor.creditCardNO  -> EmployeeManagement.Monitor.creditCardNO
    public static String getNode(NodeId nodeId) {
        return nodeId.toString().substring(nodeId.toString().lastIndexOf("=") + 1).trim();
    }

    //  获得节点名   ns=2;s=EmployeeManagement.Monitor.creditCardNO  -> Monitor
    public static String getObject(NodeId nodeId) {
        return nodeId.toString().substring(nodeId.toString().lastIndexOf("=") + 1).split("\\.")[1];
    }
}
