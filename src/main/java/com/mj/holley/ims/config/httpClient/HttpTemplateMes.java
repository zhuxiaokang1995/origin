package com.mj.holley.ims.config.httpClient;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.*;

import java.net.URI;
import java.util.Collections;

/**
 * 对接第三方系统
 *
 * @author
 * @date 2017/1/17
 */

public class HttpTemplateMes extends RestTemplate {
    public static final int MAX_ATTEMPTS = 3;
    public static final long BACK_OFF_PERIOD = 500;

    //add wms parameter when connection
    private String mesApiHost;
    private int mesApiPort;
    private String mesApiHttpSchemeHierarchical;

    // retry template
    private final RetryTemplate retryTemplate = new RetryTemplate();

    public HttpTemplateMes(ClientHttpRequestFactory clientHttpRequestFactory,
                           String mesApiHost,
                           int mesApiPort) {
        super(clientHttpRequestFactory);
        this.mesApiHost = mesApiHost;
        this.mesApiPort = mesApiPort;
        this.mesApiHttpSchemeHierarchical = "http://" + this.mesApiHost + ":" + this.mesApiPort;

        // retry policy
        SimpleRetryPolicy policy = new SimpleRetryPolicy(MAX_ATTEMPTS,
            Collections.<Class<? extends Throwable>, Boolean>singletonMap(ResourceAccessException.class, true));
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(BACK_OFF_PERIOD);
        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
    }

    public String getMesApiHost() {
        return mesApiHost;
    }

    public void setMesApiHost(String mesApiHost) {
        this.mesApiHost = mesApiHost;
    }

    public int getMesApiPort() {
        return mesApiPort;
    }

    public void setMesApiPort(int mesApiPort) {
        this.mesApiPort = mesApiPort;
    }

    public String getMesApiHttpSchemeHierarchical() {
        return mesApiHttpSchemeHierarchical;
    }

    public void setMesApiHttpSchemeHierarchical(String MesApiHttpSchemeHierarchical) {
        this.mesApiHttpSchemeHierarchical = mesApiHttpSchemeHierarchical;
    }

    public RetryTemplate getRetryTemplate() {
        return retryTemplate;
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {
        return retryTemplate.execute(
            context -> HttpTemplateMes.super.doExecute(url, method, requestCallback, responseExtractor));
    }
}
