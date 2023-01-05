package com.clownfish7.springbootmqtt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class SpringbootMqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMqttApplication.class, args);
    }

}
