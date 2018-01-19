package com.mj.holley.ims.config.opcua;


import com.mj.holley.ims.opcua.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Wanghui on 2017/5/20.
 */
@Configuration
public class OpcUaConfiguration {

    @Bean
    OpcUaClientFactory opcUaClientFactory(OpcUaProperties opcUaProperties) {
        AutoReconnectUaClientFactory opcUaClientFactory = new AutoReconnectUaClientFactory();
        opcUaClientFactory.setUaAddress(opcUaProperties.getAddress());
        return opcUaClientFactory;
    }

    @Bean(destroyMethod = "close")
    OpcUaClientTemplate opcUaClientTemplate(OpcUaClientFactory opcUaClientFactory, OpcUaProperties opcUaProperties)
        throws OpcUaClientException {
        return new OpcUaClientTemplate(opcUaClientFactory, opcUaProperties);
    }

    @Bean
    OpcUaSubscribeNodes opcUaSubscribeNodes(OpcUaProperties opcUaProperties) throws OpcUaClientException {
        return new OpcUaSubscribeNodes(opcUaProperties);
    }
}
