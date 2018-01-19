package com.mj.holley.ims.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuhusheng
 * @date 2016/11/11
 */
@Configuration
public class RabbitmqConfiguration {

    @Bean
    ConnectionFactory rabbitConnectionFactory(RabbitmqProperties properties) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(properties.getHostname(),
            properties.getPort());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        return new RabbitTemplate(rabbitConnectionFactory);
    }

    @Bean
    Queue queue(RabbitmqProperties properties) {
        return new Queue(properties.getQueueName(), false);
    }

    @Bean
    TopicExchange exchange(RabbitmqProperties properties) {
        return new TopicExchange(properties.getDefaultTopicExchange());
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange, RabbitmqProperties properties) {
        return BindingBuilder.bind(queue).to(exchange).with(properties.getQueueName());
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter,
                                             RabbitmqProperties properties) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(properties.getQueueName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    AmqpMessageReceiver receiver() {
        return new AmqpMessageReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AmqpMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
