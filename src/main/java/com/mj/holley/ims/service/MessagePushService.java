package com.mj.holley.ims.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * @author liumin
 */
@Slf4j
@Data
@Service
public class MessagePushService {

    public static final String EXCHANGE = "amq.topic";
    public static final String ROUTING_KEY = "amq.topic.";
    public static final String EXCHANGE_ALTITUDE_LOGISTICS = "whirlpool-exchange";
    public static final String ROUTING_KEY_ALTITUDE_LOGISTICS = "ControlStatus";

    //所有的推送主题都要放在此处!
    protected static final String TASKS_GREEN = "producing_task_green";
    protected static final String TASKS_RED = "producing_task_red";

    protected static final String UPGRADE_APK = "upgrade_apk";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePushService.class);

    @Inject
    private RabbitTemplate rabbitTemplate;

    @Inject
    private CacheManager cacheManager;

    private ObjectMapper objectMapper;

//    /**
//     * 向所有一体机推送更新
//     *
//     * @param documentMetadata
//     */
//    public void pushUpgradeApk(DocumentMetadata documentMetadata) {
//        sendObjectJson(UPGRADE_APK, documentMetadata);
//    }

    public MessagePushService() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        module.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        objectMapper.registerModule(module);
    }

    public boolean sendObjectJson(String topic, Object object) {
        try {
            log.debug("topic : " + topic + " | Json : " + objectMapper.writeValueAsString(object));
            return sendMessage(topic, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("Failed to convert an object to json: ", e);
        }
        return false;
    }

    public boolean sendMessage(String topic, String message) {
        try {
            this.rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY + topic, message);
//            this.rabbitTemplate.convertAndSend(message);
            return true;
        } catch (Exception ex) {
            log.error("Error sent messge: ", ex);
        }
        return false;
    }


    public boolean sendMessageToAltitudeLogistics(String message) {
        try {

            this.rabbitTemplate.convertAndSend(EXCHANGE_ALTITUDE_LOGISTICS, ROUTING_KEY_ALTITUDE_LOGISTICS, message);
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error sent message ", ex);
        }
        return false;
    }

}
