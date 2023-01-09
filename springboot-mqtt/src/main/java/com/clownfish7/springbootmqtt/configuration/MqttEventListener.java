package com.clownfish7.springbootmqtt.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.event.MqttMessageDeliveredEvent;
import org.springframework.integration.mqtt.event.MqttMessageSentEvent;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.stereotype.Component;

/**
 * classname MqttEventListener
 * description TODO
 * create 2023-01-06 10:40
 */
@Component
public class MqttEventListener {

    @EventListener
    public void listener(MqttSubscribedEvent e) {
        System.out.println("MqttSubscribedEvent ... ");
    }

    @EventListener
    public void listener(MqttMessageDeliveredEvent e) {
        System.out.println("MqttMessageDeliveredEvent ... ");
    }

    @EventListener
    public void listener(MqttMessageSentEvent e) {
        System.out.println("MqttMessageSentEvent ... ");
    }

    @EventListener
    public void listener(MqttConnectionFailedEvent e) {
        System.out.println("MqttConnectionFailedEvent ... ");
    }
}
