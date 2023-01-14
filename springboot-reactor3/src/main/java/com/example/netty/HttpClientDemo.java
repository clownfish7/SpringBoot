package com.example.netty;

import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * classname HttpClientDemo
 * description TODO
 * create 2023-01-13 13:04
 */
public class HttpClientDemo {
    public static void main(String[] args) throws InterruptedException {
        HttpClient client = HttpClient.create()
                .host("localhost")
                .port(8802);

        client.get()
                .uri("/hello")
                .responseContent()
                .aggregate()
                .asString()
                .subscribe(System.out::println);

        client.post()
                .uri("/echo")
                .send(ByteBufFlux.fromString(Flux.just("a")))
                .responseContent()
                .aggregate()
                .asString()
                .subscribe(System.out::println);

        Thread.currentThread().join();
    }
}
