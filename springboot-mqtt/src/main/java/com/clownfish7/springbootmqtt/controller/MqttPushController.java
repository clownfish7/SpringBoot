package com.clownfish7.springbootmqtt.controller;

import com.clownfish7.springbootmqtt.configuration.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * classname MqttPushController
 * description TODO
 * create 2023-01-05 11:53
 */
@RestController
public class MqttPushController {

    @Autowired
    private MqttGateway mqttGateway;

    @RequestMapping("/push")
    public String push() {
        mqttGateway.sendToMqtt("push");
        return "ok";
    }

    @RequestMapping("/push/{topic}")
    public String pushTopic(@PathVariable String topic) {
        mqttGateway.sendToTopic("pushTopic", topic);
        return "ok";
    }

    @RequestMapping("/push/{topic}/{qos}")
    public String pushTopicQos(@PathVariable String topic, @PathVariable Integer qos) {
        mqttGateway.sendToTopic("pushTopicQos", topic, qos);
        return "ok";
    }
}
