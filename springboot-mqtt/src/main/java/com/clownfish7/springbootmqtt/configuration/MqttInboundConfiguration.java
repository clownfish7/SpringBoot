package com.clownfish7.springbootmqtt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * classname MqttOutboundConfiguration
 * description TODO
 * create 2023-01-05 10:12
 */
@Configuration
public class MqttInboundConfiguration {

    @Autowired
    private MqttConfiguration mqttConfiguration;
    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        String[] inboundTopics = mqttConfiguration.getTopics().split(",");
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttConfiguration.getClientId() + "_inbound", mqttClientFactory, inboundTopics);  //对inboundTopics主题进行监听
        adapter.setCompletionTimeout(5000);
        adapter.setQos(1);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttInboundChannel());
        adapter.getConnectionInfo();
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("----------------------");
                System.out.println("message:" + message.getPayload());
                String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
                System.out.println("topic:" + topic);
            }
        };
    }


}
