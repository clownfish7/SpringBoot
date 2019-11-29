package com.clownfish7.springbootnetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author yzy
 * @classname WebSocketServer
 * @description TODO
 * @create 2019-11-07 10:05 AM
 */
@Component
public class WebSocketServer {

    private EventLoopGroup bossGroup;           // 主线程池
    private EventLoopGroup workerGroup;         // 工作线程池
    private ServerBootstrap serverBootstrap;    // 服务器
    private ChannelFuture channelFuture;        // 回调

    public WebSocketServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new WebSocketInitializer());

    }

    public void start() {
        try {
            channelFuture = serverBootstrap.bind(8899).sync();
            System.out.println("netty - server -> start success on port: 8899");
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start(int port) {
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("netty - server -> start success on port: " + port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("netty - server -> stop");
    }
}
