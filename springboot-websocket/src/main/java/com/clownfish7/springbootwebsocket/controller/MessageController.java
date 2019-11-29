package com.clownfish7.springbootwebsocket.controller;

import com.clownfish7.springbootwebsocket.service.WebSocketServiceImpl2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author yzy
 * @classname MessageController
 * @description TODO
 * @create 2019-11-28 4:46 PM
 */
@RestController
public class MessageController {

    @Autowired
    WebSocketServiceImpl2 webSocketServiceImpl2;

    @RequestMapping("/msg")
    public String msg(String msg) throws IOException {
        WebSocketServiceImpl2.webSocketSet.forEach(item->{
            try {
                item.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "ok";
    }
}
