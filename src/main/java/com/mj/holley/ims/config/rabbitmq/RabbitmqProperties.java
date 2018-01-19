package com.mj.holley.ims.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuhusheng
 * @date 2016/11/11
 */
@Data
@ConfigurationProperties(prefix = "rabbitmq", ignoreUnknownFields = false)
public class RabbitmqProperties {

    private String hostname = "localhost";

    private int port = 5672;

    private String username = "guest";

    private String password = "guest";

    private String queueName = "spring-boot";

    private String defaultTopicExchange = "sprint-boot-exchange";
}
