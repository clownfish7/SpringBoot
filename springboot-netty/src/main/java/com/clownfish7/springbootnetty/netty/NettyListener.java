package com.clownfish7.springbootnetty.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yzy
 * @classname NettyListener
 * @description TODO
 * @create 2019-11-07 10:08 AM
 */
@Component
public class NettyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            webSocketServer.start();
            WebSocketServer wss = new WebSocketServer();
            wss.start(8898);
        }
    }
}
