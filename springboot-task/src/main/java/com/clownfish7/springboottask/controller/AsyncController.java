package com.clownfish7.springboottask.controller;

import com.clownfish7.springboottask.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author You
 * @create 2019-07-07 21:23
 */
@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        asyncService.hello();
        return "success";
    }
}
