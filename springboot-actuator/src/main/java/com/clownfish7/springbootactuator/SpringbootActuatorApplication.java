package com.clownfish7.springbootactuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自定义健康状态指示器
 * 1.编写一个指示器 实现HealthIndicator接口
 * 2.指示器名字 xxxHealthIndicator
 * 3.加入容器中
 */
@SpringBootApplication
public class SpringbootActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActuatorApplication.class, args);
    }

}
