package com.mj.holley.ims.opcua;

import com.mj.holley.ims.config.opcua.OpcUaProperties;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.*;
import com.prosysopc.ua.nodes.UaDataType;
import com.prosysopc.ua.nodes.UaNode;
import com.prosysopc.ua.nodes.UaVariable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liumin
 */
@Data
@Slf4j
public class OpcUaClientTemplate {

    private UaClient uaClient;

    // private Subscription defaultSubscription;

    private RetryTemplate retryTemplate;

    private long connBackOffPeriod;

    private List<OpcUaClientConnectionListener> connectionListeners = new ArrayList<>();

    private OpcUaProperties properties;

    public OpcUaClientTemplate(OpcUaClientFactory opcUaClientFactory, OpcUaProperties properties)
        throws OpcUaClientException {
        log.debug("OpcUaClientTemplate Load.");
        uaClient = opcUaClientFactory.createUaClient();

        connBackOffPeriod = properties.getRetry().getConnBackOffPeriod();

        retryTemplate = new RetryTemplate();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        //how many attempts
        simpleRetryPolicy.setMaxAttempts(properties.getRetry().getMaxAttempts());
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        //how much time (in milliseconds) before next attempt
        fixedBackOffPolicy.setBackOffPeriod(properties.getRetry().getBackOffPeriod());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        this.properties = properties;
    }

    @Async("taskExecutor")
    public void connectAlwaysInBackend() {
        log.debug("OpcUaClientTemplate connectAlwaysInBackend taskExecutor run.");
        RetryTemplate alwaysRetryTemplate = new RetryTemplate();
        alwaysRetryTemplate.setRetryPolicy(new AlwaysRetryPolicy());
        FixedBackOffPolicy connFixedBackOffPolicy = new FixedBackOffPolicy();
        connFixedBackOffPolicy.setBackOffPeriod(connBackOffPeriod);
        alwaysRetryTemplate.setBackOffPolicy(connFixedBackOffPolicy);
        try {
            alwaysRetryTemplate.execute(context -> connect());
        } catch (OpcUaClientException e) {
            log.error("Error connecting ua server: ", e);
        }
    }

    public synchronized boolean connect() throws OpcUaClientException {
        if (!uaClient.isConnected()) {
            try {
                log.info("Connecting ua server...");
                uaClient.connect();
                fireConnectionListeners();
                return true;
            } catch (Exception e) {
                throw new OpcUaClientException("Error connecting ua server: ", e);
            }
        } else {
            return true;
        }
    }

    public void addConnectionListener(OpcUaClientConnectionListener connectionListener) {
        log.info("add opcua connection listener {}", connectionListener);
        this.connectionListeners.add(connectionListener);
    }

    private void fireConnectionListeners() throws OpcUaClientException {
        log.info("fireConnectionListeners, length {}", this.connectionListeners.size());
        this.connectionListeners.stream().forEach(OpcUaClientConnectionListener::onConnected);
    }

    public <T> T execute(final OpcUaClientCallback<T> callback) throws OpcUaClientException {
        if (this.connectionListeners.size() == 0) {
            throw new OpcUaClientException("Error executing, connectionListeners unload, uaClient not init already.");
        }
        try {
            return this.retryTemplate.execute(context -> {
                connect();
                return callback.performAction();
            });
        } catch (Exception e) {
            log.error("execute()", e);
            throw new OpcUaClientException("Error executing action: ", e);
        }
    }

    public boolean subscribeNodeValue(NodeId id, MonitoredDataItemListener dataChangeListener)
        throws OpcUaClientException {
        return execute(() -> doSubscribeNodeValue(id, dataChangeListener));
    }

    private synchronized boolean doSubscribeNodeValue(NodeId id, MonitoredDataItemListener dataChangeListener)
        throws OpcUaClientException {
        try {
            Subscription subscription = new Subscription();
            subscription.setPublishingInterval(properties.getPublishingRate(), TimeUnit.MILLISECONDS);
            MonitoredDataItem item = new MonitoredDataItem(id, Attributes.Value,
                MonitoringMode.Reporting, subscription.getPublishingInterval());
            item.setDataChangeListener(dataChangeListener);
            subscription.addItem(item);
            uaClient.addSubscription(subscription);
            return true;
        } catch (ServiceException | StatusException e) {
            throw new OpcUaClientException("Error subscribing node " + id + ", value: ", e);
        }
    }

    public boolean writeNodeValue(NodeId id, Object object) throws OpcUaClientException {
        return execute(() -> doWriteNodeValue(id, object));
    }

    private boolean doWriteNodeValue(NodeId nodeId, Object value) throws OpcUaClientException {
        try {
            UnsignedInteger attributeId = Attributes.Value;
            UaNode node = uaClient.getAddressSpace().getNode(nodeId);
            // Find the DataType if setting Value - for other properties you must
            // find the correct data type yourself
            UaDataType dataType = null;
            if (attributeId.equals(Attributes.Value) && (node instanceof UaVariable)) {
                UaVariable v = (UaVariable) node;
                // Initialize DataType node, if it is not initialized yet
                if (v.getDataType() == null) {
                    v.setDataType(uaClient.getAddressSpace().getType(v.getDataTypeId()));
                }
                dataType = (UaDataType) v.getDataType();
            }

            // 如果value是数组
            if (value.getClass().isArray()) {
                Object[] array = (Object[]) value;
                Object newArray = null;
                for (int i = 0; i < array.length; i++) {
                    Object el = dataType != null
                        ? uaClient.getAddressSpace()
                        .getDataTypeConverter()
                        .parseVariant(array[i].toString(), dataType)
                        : value;
                    Object v = ((Variant) el).getValue();
                    if (newArray == null) {
                        newArray = Array.newInstance(v.getClass(), array.length);
                    }
                    Array.set(newArray, i, v);
                }
                return uaClient.writeAttribute(nodeId, attributeId, newArray);
            }

            Object convertedValue = dataType != null
                ? uaClient.getAddressSpace().getDataTypeConverter().parseVariant(value.toString(), dataType) : value;
            return uaClient.writeAttribute(nodeId, attributeId, convertedValue);
        } catch (AddressSpaceException | ServiceException | StatusException e) {
            throw new OpcUaClientException("Error writing node value: ", e);
        }
    }

    public Variant readNodeVariant(NodeId id) throws OpcUaClientException {
        return execute(() -> doReadNodeValue(id));
    }

    private Variant doReadNodeValue(NodeId id) throws OpcUaClientException {
        try {
            DataValue dataValue = uaClient.readValue(id);
            return dataValue.getValue();
        } catch (ServiceException | StatusException e) {
            throw new OpcUaClientException("Error reading " + id + " node value: ", e);
        }
    }

    public <T> T readNodeObject(String nodeIdStr, Class<T> clazz) throws OpcUaClientException {
        return execute(() -> doReadNodeObject(nodeIdStr, clazz));
    }

    private <T> T doReadNodeObject(String nodeIdStr, Class<T> clazz) throws OpcUaClientException {
        try {
            T t = clazz.newInstance();
            List<Field> fieldList = new ArrayList<>();
            OpcUaObject opcUaObject = clazz.getAnnotation(OpcUaObject.class);
            if (opcUaObject == null && nodeIdStr == null) {
                return t;
            }
            // NodeId objNodeId = nodeIdStr == null ? NodeId.parseNodeId(opcUaObject.id()) : NodeId.parseNodeId(nodeIdStr);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(OpcUaIgnore.class) != null) {
                    continue;
                }
                if (field.getAnnotation(OpcUaPosToVar.class) != null) {
                    fieldList.add(field);
                    continue;
                }
                OpcUaVariable opcUaVariable = field.getAnnotation(OpcUaVariable.class);

                NodeId variableId = opcUaVariable != null ? NodeId.parseNodeId(opcUaVariable.id()) : NodeId.parseNodeId(nodeIdStr + "." + field.getName());

                Object value = readNodeVariant(variableId).getValue();
                Class enumClass = (Class) field.getGenericType();
                Object[] enumConstants = enumClass.getEnumConstants();
                OpcUaEnum opcUaEnum = field.getAnnotation(OpcUaEnum.class);
                if (opcUaEnum != null) {
                    OpcUaEnumMapping[] table = opcUaEnum.table();
                    for (OpcUaEnumMapping mapping : table) {
                        if (mapping.uaValue().equalsIgnoreCase(value.toString())) {
                            for (Object enumConstant : enumConstants) {
                                if (enumConstant.toString().equalsIgnoreCase(mapping.enumValue())) {
                                    field.setAccessible(true);
                                    field.set(t, enumConstant);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    field.setAccessible(true);
                    field.set(t, field.getType().getMethod("valueOf", String.class).invoke(null, value.toString()));
                }
            }
            for (Field field : fieldList) {
                OpcUaPosToVar opcUaPosToVar = field.getAnnotation(OpcUaPosToVar.class);
                Field opcUaVariableField = clazz.getDeclaredField(opcUaPosToVar.variableName());
                opcUaVariableField.setAccessible(true);
                Integer val = Integer.valueOf(opcUaVariableField.get(t).toString());
                field.setAccessible(true);
                field.set(t, ((val >> opcUaPosToVar.position()) & 1) != 0);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new OpcUaClientException("Error reading node object: ", e);
        } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            throw new OpcUaClientException("new Error reading node object : ", e);
        }
    }

    public <T> T doReadNodeObject(Class<T> clazz) throws OpcUaClientException {
        return doReadNodeObject(null, clazz);
    }

    public void close() {
        uaClient.disconnect();
    }
}
