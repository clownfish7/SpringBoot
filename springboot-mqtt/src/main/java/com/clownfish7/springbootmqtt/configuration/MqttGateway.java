package com.clownfish7.springbootmqtt.configuration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * classname MqttGateway
 * description TODO
 * create 2023-01-05 11:55
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    void sendToMqtt(String payload);

    void sendToTopic(String payload, @Header(MqttHeaders.TOPIC) String topic);

    void sendToTopic(String payload, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos);
}
