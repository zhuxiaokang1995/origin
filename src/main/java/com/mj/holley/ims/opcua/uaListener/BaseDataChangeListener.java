package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.opcua.util.OpcUaUtil;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;

import javax.inject.Inject;

/**
 * Created by Wanghui on 2017/5/20.
 */
@Slf4j
public abstract class BaseDataChangeListener implements MonitoredDataItemListener {

    protected DataValue dataValueOld;

    @Inject
    protected OpcUaClientTemplate opcUaClientTemplate;


    protected abstract void sendAndSave(String nodeId, DataValue dataValueNew) throws OpcUaClientException;

    @Override
    public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValueNew) {
        this.dataValueOld = dataValue;
        String nodeId = monitoredDataItem.getNodeId().toString();
        if (!OpcUaUtil.isNewNodeValueValid(monitoredDataItem.getNodeId(), dataValue, dataValueNew)) {
            return;
        }
        try {
            sendAndSave(nodeId, dataValueNew);
        } catch (OpcUaClientException e) {
            log.error("Read uaNode Object Error while sendAndSave {}", monitoredDataItem.getNodeId(), e);
        }
    }

    protected String parentNodeId(String nodeId) {
        return nodeId.substring(0, nodeId.lastIndexOf("."));
    }

    protected String toNonOpcuaStyleNodeId(String uaNodeId) {
        return uaNodeId.split("=")[2];
    }

}
