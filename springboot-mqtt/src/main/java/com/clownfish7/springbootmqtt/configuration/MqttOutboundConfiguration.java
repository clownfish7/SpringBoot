package com.clownfish7.springbootmqtt.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.event.MqttMessageDeliveredEvent;
import org.springframework.integration.mqtt.event.MqttMessageSentEvent;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * classname MqttOutboundConfiguration
 * description TODO
 * create 2023-01-05 10:12
 */
@Configuration
public class MqttOutboundConfiguration {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        String[] array = mqttConfiguration.getUrl().split(",");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(array);
        options.setUserName(mqttConfiguration.getUsername());
        options.setPassword(mqttConfiguration.getPassword().toCharArray());
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                mqttConfiguration.getClientId() + "outbound", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setAsyncEvents(true);
        return messageHandler;
    }


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
