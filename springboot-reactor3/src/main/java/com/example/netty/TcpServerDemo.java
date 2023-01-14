package com.example.netty;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import reactor.netty.ChannelPipelineConfigurer;
import reactor.netty.Connection;
import reactor.netty.ConnectionObserver;
import reactor.netty.tcp.TcpServer;

import java.net.SocketAddress;

/**
 * classname TcpServerDemo
 * description TODO
 * create 2023-01-11 17:15
 */
public class TcpServerDemo {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.create()
                .host("localhost")
                .port(8801)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .observe(new ConnectionObserver() {
                    @Override
                    public void onStateChange(Connection conn, State state) {
                        System.out.println("main state=" + state + " conn=" + conn.channel().remoteAddress());
                    }
                })
                .childObserve((conn, state) -> {
                    System.out.println("child state=" + state + " conn=" + conn.channel().remoteAddress());
                })
//                .handle((in, out) ->
//                        in.receiveObject()
//                                .cast(ByteBuf.class)
//                                .doOnNext(buf -> {
//                                    System.out.println("receive: " + ByteBufUtil.hexDump(buf));
//                                })
//                                .then()
//                        in.receive()
//                                .doOnNext(buf -> {
//                                    System.out.println("receive: " + ByteBufUtil.hexDump(buf));
//                                })
//                                .then()
//
//                )
                .doOnConnection(conn -> {
                    System.out.println("当一个远程客户端连接上的时候被调用");
                    conn.addHandlerLast(new LoggingHandler("lllloooogggg2"))
                            .addHandlerLast(new DelimiterBasedFrameDecoder(8192, false, Unpooled.buffer().writeByte(0x5a)));
                    conn.onReadIdle(5000, () -> {
                        System.out.println("read time out");
                    });
                    conn.inbound().receive().subscribe(buf -> {
                        System.out.println("receive: " + ByteBufUtil.hexDump(buf));
                    });
                })
                .doOnChannelInit(new ChannelPipelineConfigurer() {
                    @Override
                    public void onChannelInit(ConnectionObserver connectionObserver, Channel channel, SocketAddress socketAddress) {
                        System.out.println("当channel初始化的时候被调用");
                        channel.pipeline().addLast(new LoggingHandler("lllloooogggg"));
                    }

                })

                .doOnBind(tcpServerConfig -> {
                    System.out.println("当服务器channel即将被绑定的时候调用");
                })
                .doOnBound(server -> {
                    System.out.println("当服务器channel已经被绑定的时候调用");
                })
                .doOnUnbound(server -> {
                    System.out.println("当服务器channel解绑的时候被调用");
                });

        tcpServer.bindNow()
                .onDispose()
                .block();

    }
}
