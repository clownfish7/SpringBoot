package com.clownfish7.springbootstomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author yzy
 * @classname WebSocketController
 * @description TODO
 * @create 2019-11-28 5:20 PM
 */
@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    @Autowired // 注入springboot自动配置消息模板
    private SimpMessagingTemplate simpMessagingTemplate;

    // 定义消息请求路径
    @MessageMapping("/send")
    // 定义结果发送到特定路径
    @SendTo("/sub/chat")
    public String sendMsg(String msg) {
        return msg;
    }

    // 将消息发送给特定用户
    @MessageMapping("/sendUser")
    public void sendToUser(Principal principal, String body) {
        String name = principal.getName();
        // 解析用户和消息
        String[] args = body.split(",");
        String user = args[0];
        String msg = "[" + user + "] 发来消息：" + args[1];
        // 发送到用户和监听地址
        simpMessagingTemplate.convertAndSendToUser(user, "/queue/customer", msg);
    }
}
