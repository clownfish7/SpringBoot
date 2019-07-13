package com.clownfish7.springbootdevtools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author You
 * @create 2019-07-13 16:22
 */
@Controller
public class HelloController {

    //ctrl+f9 -> build
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
