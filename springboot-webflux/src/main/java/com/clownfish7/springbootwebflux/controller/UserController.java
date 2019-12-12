package com.clownfish7.springbootwebflux.controller;

import com.clownfish7.springbootwebflux.pojo.SexEnum;
import com.clownfish7.springbootwebflux.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author yzy
 * @classname UserController
 * @description TODO
 * @create 2019-12-02 2:40 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setNote("note");
        user.setUserName("yzy");
        user.setSex(SexEnum.MALE);
        return Mono.just(user);
    }
}
