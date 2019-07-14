package com.clownfish7.springbootdubboconsumeruser;

import com.clownfish7.springbootdubboconsumeruser.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDubboConsumerUserApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        System.out.println("1");
        userService.hello();
    }
}
