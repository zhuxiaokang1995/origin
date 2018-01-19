package com.mj.holley.ims.config.httpClient;

import com.mj.holley.ims.domain.util.CamelCaseMappingJacksonHttpMessageConverter;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.*;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liumin
 */
@Configuration
public class HttpClientConfiguration {

    @Bean(destroyMethod = "close")
    public HttpClientConnectionManager httpClientConnectionManager(HttpClientProperties httpClientProperties) {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(httpClientProperties.getPool().getMaxTotal());
        httpClientConnectionManager.setDefaultMaxPerRoute(httpClientProperties.getPool().getDefaultMaxPerRoute());
        return httpClientConnectionManager;
    }

    @Bean(destroyMethod = "shutdown")
    public Thread idleConnectionMonitorThread(HttpClientConnectionManager connectionManager) {
        Thread idleConnectionMonitorThread = new Thread() {
            private volatile boolean shutdown;

            @Override
            public void run() {
                try {
                    while (!shutdown) {
                        synchronized (this) {
                            wait(10000);
                            // Close expired connections
                            connectionManager.closeExpiredConnections();
                            // Optionally, close connections
                            // that have been idle longer than 360 sec
                            connectionManager.closeIdleConnections(360, TimeUnit.SECONDS);
                        }
                    }
                } catch (InterruptedException ex) {
                    // terminate
                }
            }

            public void shutdown() {
                shutdown = true;
                synchronized (this) {
                    notifyAll();
                }
            }
        };
        idleConnectionMonitorThread.start();
        return idleConnectionMonitorThread;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager connectionManager,
                                               HttpClientProperties httpClientProperties) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(connectionManager);
        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientProperties.getRetry().getRetryCount(),
            httpClientProperties.getRetry().isRequestSentRetryEnabled()));
        return builder;
    }

    @Bean
    public HttpClient httpClient(HttpClientBuilder builder, HttpClientProperties httpClientProperties) {
        builder.setKeepAliveStrategy((HttpResponse response, HttpContext context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(
                HttpClientContext.HTTP_TARGET_HOST);
            if (httpClientProperties.getMes().getHost().equalsIgnoreCase(target.getHostName())) {
                // Keep alive for 360 seconds only
                return 360000;
            } else {
                // otherwise keep alive for 60 seconds
                return 60000;
            }
        });
        return builder.build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient,
                                                             HttpClientProperties httpClientProperties) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(httpClientProperties.getRequest().getConnectTimeout());
        clientHttpRequestFactory.setReadTimeout(httpClientProperties.getRequest().getReadTimeout());
        clientHttpRequestFactory.setConnectionRequestTimeout(
            httpClientProperties.getRequest().getConnectionRequestTimeout());
        return clientHttpRequestFactory;
    }

    // 对接的第三方Restful Web Service
    @Bean(name = "httpTemplateMes")
    public HttpTemplateMes httpTemplateMes(ClientHttpRequestFactory clientHttpRequestFactory,
                                           HttpClientProperties httpClientProperties) {
        HttpTemplateMes httpTemplateMes = new HttpTemplateMes(clientHttpRequestFactory,
            httpClientProperties.getMes().getHost(), httpClientProperties.getMes().getPort());
        httpTemplateMes.setErrorHandler(new DefaultResponseErrorHandler());
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new CamelCaseMappingJacksonHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        httpTemplateMes.setMessageConverters(messageConverters);
        return httpTemplateMes;
    }

}
