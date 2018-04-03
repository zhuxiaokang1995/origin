package com.mj.holley.ims.config.opcua;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * * Properties specific to OpcUa Client.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 * <p>
 * Created by Wanghui on 2017/5/20.
 */
@Data
@ConfigurationProperties(prefix = "opcua", ignoreUnknownFields = false)
public class OpcUaProperties {

    private String address;

    private Retry retry;

    private long publishingRate;

    //TODO: [all] Add list subscribe nodes here
//    private List<String> testSubscribeNodes;
//    private List<String> employeeSubscribeNodes;
//    private List<String> branchSelectSubscribeNodes;
//    private List<String> cartonSpaceSubscribeNodes;
//    private List<String> fridgeListSubscribeNodes;
//    private List<String> printerSubscribeNodes;
//    private List<String> cartonBarcodeSubscribeNodes;
//    private List<String> warningSubscribeNodes;
    private List<String> SubscribeNodesSubscribeNodes;
    private List<String> scanSignalSubscribeNodes;
    private List<String> transportSignalSubscribeNodes;

    public static class Retry {
        private long connBackOffPeriod = 10000L;
        private int maxAttempts = 3;
        private long backOffPeriod = 1000L;

        public long getConnBackOffPeriod() {
            return connBackOffPeriod;
        }

        public void setConnBackOffPeriod(long connBackOffPeriod) {
            this.connBackOffPeriod = connBackOffPeriod;
        }

        public int getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public long getBackOffPeriod() {
            return backOffPeriod;
        }

        public void setBackOffPeriod(long backOffPeriod) {
            this.backOffPeriod = backOffPeriod;
        }
    }
}
