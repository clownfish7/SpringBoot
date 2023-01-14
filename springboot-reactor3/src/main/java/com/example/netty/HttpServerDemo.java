package com.example.netty;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * classname HttpServerDemo
 * description TODO
 * create 2023-01-13 13:04
 */
public class HttpServerDemo {
    public static void main(String[] args) throws URISyntaxException {
        Path file = Paths.get(HttpServerDemo.class.getResource("/application.properties").toURI());
        DisposableServer server = HttpServer.create()
                .compress(true)
                .host("localhost")
                .port(8802)
                .route(routes -> {
                    routes
                            .get("/hello", (request, response) -> response.sendString(Mono.just("Hello World!")))
                            .post("/echo", (request, response) -> response.send(request.receive().retain()))
                            .get("/path/{param}", (request, response) -> response.sendString(Mono.just(request.param("param"))))
                            .ws("/ws", (wsInbound, wsOutBound) -> wsOutBound.send(wsInbound.receive().retain()))
                            .file("/index.html", file)
                            .post("/xx",(request, response) ->{
//                                request.requestHeaders();
                                return request.receive()
                                        .asString()

                                        .doOnNext(System.out::println)
                                        .then(response.sendNotFound())
                                ;
                            })
                    ;
                })
                .bindNow();

        server.onDispose().block();
    }
}
