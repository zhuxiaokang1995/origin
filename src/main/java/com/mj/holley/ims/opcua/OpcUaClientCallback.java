package com.mj.holley.ims.opcua;

/**
 * @author liumin
 */
public interface OpcUaClientCallback<T> {

    T performAction() throws Exception;
}
