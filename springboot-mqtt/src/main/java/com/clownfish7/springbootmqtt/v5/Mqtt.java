package com.clownfish7.springbootmqtt.v5;

import com.clownfish7.springbootmqtt.configuration.MqttConfiguration;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaderMapper;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.StringMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * classname Mqtt
 * description TODO
 * create 2023-01-06 10:26
 */
@Configuration
public class Mqtt {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttConnectionOptions mqttConnectionOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        String[] array = mqttConfiguration.getUrl().split(",");
        options.setServerURIs(array);
        options.setUserName(mqttConfiguration.getUsername());
        options.setPassword(mqttConfiguration.getPassword().getBytes(StandardCharsets.UTF_8));
        options.setCleanStart(false);
        options.setSessionExpiryInterval(0xffffffffL);
        options.setAutomaticReconnect(true);
        return options;
    }

    @Bean
    public MessageProducer mqttInFlow(MqttConnectionOptions options) {
        String[] inboundTopics = mqttConfiguration.getTopics().split(",");
        Mqttv5PahoMessageDrivenChannelAdapter messageProducer =
                new Mqttv5PahoMessageDrivenChannelAdapter(options, "mqttv5SIin", inboundTopics);
        messageProducer.setPayloadType(MqttMessage.class);
        messageProducer.setMessageConverter(new StringMessageConverter());
        messageProducer.setOutputChannel(mqttInboundChannel());
        return messageProducer;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler5() {
        return message -> {
            System.out.println("----------------------");
            System.out.println("message:" + message.getPayload());
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            System.out.println("topic:" + topic);
        };
    }

    @Bean
    public MessageChannel mqttOutboundChannel5() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel5")
    public MessageHandler mqttOutFlow(MqttConnectionOptions options) {
        Mqttv5PahoMessageHandler messageHandler = new Mqttv5PahoMessageHandler(options, "mqttv5SIout");
        MqttHeaderMapper mqttHeaderMapper = new MqttHeaderMapper();
        // 这个设置运行映射消息头
        mqttHeaderMapper.setOutboundHeaderNames(MqttHeaders.RESPONSE_TOPIC, MqttHeaders.CORRELATION_DATA,"head1","some_user_header", MessageHeaders.CONTENT_TYPE);
        messageHandler.setHeaderMapper(mqttHeaderMapper);
        messageHandler.setAsync(true);
        messageHandler.setAsyncEvents(true);
        messageHandler.setConverter(new StringMessageConverter());
        return messageHandler;
    }

}
