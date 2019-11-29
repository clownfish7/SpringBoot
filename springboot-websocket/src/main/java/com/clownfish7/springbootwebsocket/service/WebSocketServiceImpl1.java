package com.clownfish7.springbootwebsocket.service;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yzy
 * @classname WebSocketServiceImpl1
 * @description TODO
 * @create 2019-11-28 4:13 PM
 */

@ServerEndpoint("/ws1")
@Service
public class WebSocketServiceImpl1 {

    // 静态变,用来记录当前在线连接数 应该把它设计成线程安全的
    private static int onLineClient = 0;

    // concurrent 包的线程安全 Set ，用来存放每个客户端对应的 WebSocketServiceimpl 对象
    private static CopyOnWriteArraySet<WebSocketServiceImpl1> webSocketSet = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnLineClient();
        session.setMaxIdleTimeout(3000);
        System.out.println("有新连接加入 当前在线人数为:" + getOnLineClient());
    }


    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnLineClient();
        System.out.println("有连接关闭 当前在线人数为:" + getOnLineClient());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息： " + message);
        webSocketSet.forEach(item->{
            try {
                item.sendMessage("topic message");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误！");
        error.printStackTrace();
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static synchronized int getOnLineClient() {
        return onLineClient;
    }

    private static synchronized void addOnLineClient() {
        WebSocketServiceImpl1.onLineClient++;
    }

    private static synchronized void subOnLineClient() {
        WebSocketServiceImpl1.onLineClient--;
    }


}
