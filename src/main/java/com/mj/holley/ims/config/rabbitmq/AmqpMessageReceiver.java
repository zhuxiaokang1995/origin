package com.mj.holley.ims.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuhusheng
 * @date 2016/8/12
 */
@Slf4j
public class AmqpMessageReceiver {
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
    }
}
