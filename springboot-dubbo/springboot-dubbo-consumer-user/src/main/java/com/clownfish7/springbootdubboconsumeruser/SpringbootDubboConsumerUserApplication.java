package com.clownfish7.springbootdubboconsumeruser;

import com.clownfish7.springbootdubboconsumeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、引入依赖
 * 2、配置dubbo的注册中心地址
 * 3、引用服务
 */
@SpringBootApplication
public class SpringbootDubboConsumerUserApplication {

    @Autowired
    private static UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubboConsumerUserApplication.class, args);
        System.out.println("begin");
        userService.hello();
    }

}
