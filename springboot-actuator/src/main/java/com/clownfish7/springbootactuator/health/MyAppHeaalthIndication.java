package com.clownfish7.springbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author You
 * @create 2019-07-13 17:48
 */
@Component
public class MyAppHeaalthIndication implements HealthIndicator {

    @Override
    public Health health() {

        //自定义的检查方法
        //Health.up().build() -> 健康
        return Health.down().withDetail("msg", "服务异常").build();
    }
}
