package com.clownfish7.springbootwebsocket.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * classname WebSocket
 * description TODO
 * create 2021-04-13 10:19
 *
 * @author yzy yuzhiyou999@outlook.com
 * @version 1.0
 */
@ClientEndpoint
public class WebSocket {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String text) {
    }

    @OnError
    public void onError(Throwable throwable) {
    }

    @OnClose
    public void onClosing() throws IOException {
        session.close();
    }

    public boolean isOpen() {
        return session.isOpen();
    }

    public void sendMessage(String text) throws IOException {
        session.getBasicRemote().sendText(text);
    }

    public static WebSocket connect(String url) throws Exception {
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        WebSocket client = new WebSocket();
        wsc.connectToServer(client, URI.create(url));
        return client;
    }

}
