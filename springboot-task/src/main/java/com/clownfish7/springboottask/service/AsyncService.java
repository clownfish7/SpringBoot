package com.clownfish7.springboottask.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author You
 * @create 2019-07-07 21:22
 */
@Service
public class AsyncService {

    //告诉spring这是一个异步方法
    @Async
    public void hello() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("数据处理中...");
    }
}
