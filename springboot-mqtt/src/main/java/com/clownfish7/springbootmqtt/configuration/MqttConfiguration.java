package com.clownfish7.springbootmqtt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * classname MqttConfiguration
 * description TODO
 * create 2023-01-05 10:09
 */
@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfiguration {
    private String url;
    private String clientId;
    private String topics;
    private String username;
    private String password;
    private String timeout;
    private String keepalive;
}
