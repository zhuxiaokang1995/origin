package com.mj.holley.ims.opcua.uaListener;

import com.mj.holley.ims.opcua.OpcUaClientConnectionListener;
import com.mj.holley.ims.opcua.OpcUaClientException;
import com.mj.holley.ims.opcua.OpcUaClientTemplate;
import com.mj.holley.ims.opcua.OpcUaSubscribeNodes;
import com.prosysopc.ua.client.MonitoredDataItemListener;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Wanghui on 2017/5/20.
 */
@Slf4j
@Component
public class UaConnectionListener implements OpcUaClientConnectionListener {

    private static final Map<String, MonitoredDataItemListener> LISTENER_MAP = new ConcurrentHashMap<>();

    @Inject
    private OpcUaClientTemplate opcUaClientTemplate;

    @Inject
    private OpcUaSubscribeNodes opcUaSubscribeNodes;

    // TODO: [All] add all subscribeListener here
    @Inject
    private ScanSignalListener scanSignalListener;

    @Inject
    private ScanSignalListener9 scanSignalListener9;

    @Inject
    private TransportSignalListener transportSignalListener;

    public OpcUaClientTemplate getOpcUaClientTemplate() {
        return opcUaClientTemplate;
    }

    public void setOpcUaClientTemplate(OpcUaClientTemplate opcUaClientTemplate) {
        this.opcUaClientTemplate = opcUaClientTemplate;
    }

    public OpcUaSubscribeNodes getOpcUaSubscribeNodes() {
        return opcUaSubscribeNodes;
    }

    public void setOpcUaSubscribeNodes(OpcUaSubscribeNodes opcUaSubscribeNodes) {
        this.opcUaSubscribeNodes = opcUaSubscribeNodes;
    }

    @Override
    public void onConnected() {
        log.info("---------->>>>> opcua client connect success <<<<<-----------");

        //从plc读取各个仓位的纸箱信息
//        packingBoxService.readProductNoFromPlc();

        subscribeNodesValue(opcUaSubscribeNodes.getScanSignalSubscribeNodes(), scanSignalListener);
        subscribeNodesValue(opcUaSubscribeNodes.getScanSignalSubscribeNodes9(), scanSignalListener9);

        subscribeNodesValue(opcUaSubscribeNodes.getTransportSignalSubscribeNodes(), transportSignalListener);



    }

    private synchronized void subscribeNodesValue(List<String> StrList, MonitoredDataItemListener listener) {
        try {
            for (String nodeIdStr : StrList) {
                if (LISTENER_MAP.containsKey(nodeIdStr + ":" + listener.getClass().toString())) {
                    return;
                }
                log.debug("add listener:{}", nodeIdStr + ":" + listener.getClass().toString());
                opcUaClientTemplate.subscribeNodeValue(new NodeId(6, nodeIdStr), listener);
                LISTENER_MAP.put(nodeIdStr + ":" + listener.getClass().toString(), listener);
            }
        } catch (OpcUaClientException e) {
            log.error("OpcUa Client Exception when subscribeNodesValue", e);
        }
    }

}
