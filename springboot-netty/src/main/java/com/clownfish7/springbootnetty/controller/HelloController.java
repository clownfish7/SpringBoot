package com.clownfish7.springbootnetty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzy
 * @classname HelloController
 * @description TODO
 * @create 2019-11-07 11:48 AM
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hehe() {
        return "hi";
    }
}
