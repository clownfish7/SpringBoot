package com.example.netty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;

/**
 * classname TcpClientDemo
 * description TODO
 * create 2023-01-12 10:04
 */
public class TcpClientDemo {
    public static void main(String[] args) {
        TcpClient tcpClient = TcpClient.create()
                .host("localhost")
                .port(8801)
                .observe((conn, state) -> {
                    System.out.println("child state=" + state + " conn=" + conn.channel().remoteAddress());
                })
//                .handle()
                .doOnConnect(c -> {
                    System.out.println("connect");
                })
                .doOnConnected(c -> {
                    System.out.println("connected");
                    c.addHandlerLast(new DelimiterBasedFrameDecoder(8192, false, Unpooled.buffer().writeByte(0x5a)));
//                                        c.addHandlerLast(new LineBasedFrameDecoder(1024));
//                    c.addHandlerLast(new StringDecoder(Charset.forName("GBK")));
//                    c.addHandlerLast(new StringEncoder(Charset.forName("GBK")));

//                   c.outbound().sendString(Mono.just("fuw 我")).then().subscribe();
//                    Flux.range(1, 100).subscribe(i -> c.outbound().sendString(Mono.just("fuw 我")).then().subscribe());
                    /*c.channel().eventLoop().scheduleAtFixedRate(() -> {
                        c.outbound().sendString(Mono.just("fuw 我")).then().subscribe();
                    }, 1, 1, TimeUnit.SECONDS);*/

                    Flux.interval(Duration.ofSeconds(2)).subscribe(i -> {
                        System.out.println(Thread.currentThread().getName() + " - ddddd");
                        c.outbound().send(Mono.just(Unpooled.buffer()
                                        .writeByte(0x01)
                                        .writeByte(0x02)
                                        .writeByte(0x5a)
                                        .writeByte(0x03)
                                        .writeByte(0x5a)
                                        .writeByte(0x5a)
                                        .writeByte(0x04)
                                        .writeByte(0x5a)
                                        .writeByte(0x05)
                                ))
                                .then().subscribe();
                    });

                })
                .doOnDisconnected(c -> {
                    System.out.println("disconnected");
                });

        tcpClient.connectNow()
                .onDispose()
                .block();
    }
}
