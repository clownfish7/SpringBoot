package com.clownfish7.springbootwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author yzy
 * @classname WebSocketConfig
 * @description TODO
 * @create 2019-11-28 4:12 PM
 */
@Configuration
public class WebSocketConfig {

    // 创建服务端点
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
