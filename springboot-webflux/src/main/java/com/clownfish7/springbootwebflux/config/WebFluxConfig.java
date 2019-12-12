package com.clownfish7.springbootwebflux.config;

import com.clownfish7.springbootwebflux.pojo.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author yzy
 * @classname WebFluxConfigurer
 * @description 实现JDK8的接口WebFluxConfigurer,该接口都是default方法
 * @create 2019-12-05 5:42 PM
 */
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    // 注册 Converter
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(string2UserConverter());
    }

    // 定义 string -> user 类型转换器
    // @Bean // 如果定义为Springboot Bean，SpringBoot会自动识别为类型转换器
    public Converter<String, User> string2UserConverter() {
        Converter<String, User> converter = new Converter<String, User>() {
            @Override
            public User convert(String s) {
                String[] split = s.split("-");
                User user = new User();
                user.setId(Long.parseLong(split[0]));
                return user;
            }
        };
        return converter;
    }
}
