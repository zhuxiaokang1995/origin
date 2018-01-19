package com.mj.holley.ims.config.httpClient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Httpclient.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 *
 * @author zy
 */
@ConfigurationProperties(prefix = "httpclient", ignoreUnknownFields = false)
public class HttpClientProperties {

    private final Pool pool = new Pool();

    private final Request request = new Request();

    private final Retry retry = new Retry();

    //第三方Restful WebService
    private final Mes mes = new Mes();

    public Pool getPool() {
        return pool;
    }

    public Request getRequest() {
        return request;
    }

    public Retry getRetry() {
        return retry;
    }

    public Mes getMes() {
        return mes;
    }


    public static class Pool {
        private int maxTotal = 200;
        private int defaultMaxPerRoute = 100;

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getDefaultMaxPerRoute() {
            return defaultMaxPerRoute;
        }

        public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
            this.defaultMaxPerRoute = defaultMaxPerRoute;
        }
    }

    public static class Request {
        private int connectTimeout = 2000;
        private int readTimeout = 2000;
        private int connectionRequestTimeout = 100;

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getConnectionRequestTimeout() {
            return connectionRequestTimeout;
        }

        public void setConnectionRequestTimeout(int connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
        }
    }

    public static class Retry {
        private int retryCount = 3;
        private boolean requestSentRetryEnabled = true;

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public boolean isRequestSentRetryEnabled() {
            return requestSentRetryEnabled;
        }

        public void setRequestSentRetryEnabled(boolean requestSentRetryEnabled) {
            this.requestSentRetryEnabled = requestSentRetryEnabled;
        }
    }

    public static class Mes {
        private String host = "localhost";
        private int port = 8088;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
