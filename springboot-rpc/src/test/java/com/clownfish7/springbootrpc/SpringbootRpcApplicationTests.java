package com.clownfish7.springbootrpc;

import com.clownfish7.springbootrpc.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRpcApplicationTests {

    @Autowired
    TestService testService;

    @Test
    void contextLoads() {
        int res = testService.calc(2);
        System.out.println("res=" + res);
    }

}
