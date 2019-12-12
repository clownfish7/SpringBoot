package com.clownfish7.springbootwebflux;

import com.clownfish7.springbootwebflux.pojo.SexEnum;
import com.clownfish7.springbootwebflux.pojo.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yzy
 * @classname WebClientTest
 * @description TODO
 * @create 2019-12-02 2:25 PM
 */
public class WebClientTest {

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://localhost:8080");

        User user = new User();
        user.setId(1L);
        user.setSex(SexEnum.MALE);
        user.setUserName("yzy");
        user.setNote("clownfish");

//        insertUser(webClient, user);
        getUser(webClient, 1L);
//        updateUser(webClient, user);
//        findUsers(webClient, "name","note");
//        deleteUser(webClient, 1L);
    }

    private static void insertUser(WebClient webClient, User user) {
        Mono<User> userMono = webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(user), User.class)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(User.class);
    }

    private static void deleteUser(WebClient webClient, Long id) {

    }

    private static void updateUser(WebClient webClient, User user) {
        Mono<User> userMono = webClient.put()
                .uri("/")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(user), User.class)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(User.class);
    }

    private static void getUser(WebClient webClient, Long id) {
        Mono<User> userMono = webClient.get()
                .uri("/user/{id}",id)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(User.class);
        User user = userMono.block();
        System.out.println(user);
    }

    private static void findUsers(WebClient webClient, String userName, String note) {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("userName", userName);
        map1.put("note", note);
        Flux<User> userFlux = webClient.get()
                .uri("/user/{userName}/{note}", map1)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(User.class);
        Iterator<User> iterator = userFlux.toIterable().iterator();
    }
}
