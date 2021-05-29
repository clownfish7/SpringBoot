package com.clownfish7.springbootamqp.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author You
 * @create 2019-07-07 17:18
 */
@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue("clownfish");
    }

    @Bean
    public Queue queue2() {
        return new Queue("clownfish.news");
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                String msgId = correlationData.getId();
                if (ack) {
                    System.out.println(String.format("消息 {%s} 成功发送给mq", msgId));
                } else {
                    // 可以加一些重试的逻辑
                    System.out.println(String.format("消息 {%s} 发送mq失败", msgId));
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                String msg = new String(message.getBody());
                System.out.println(String.format("消息 {%s} 不能被正确路由，routingKey为 {%s}", msg, routingKey));
            }
        });
        return rabbitTemplate;
    }
}
