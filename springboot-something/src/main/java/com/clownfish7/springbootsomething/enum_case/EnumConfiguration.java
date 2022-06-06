package com.clownfish7.springbootsomething.enum_case;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * classname EnumConfiguration
 * description TODO
 * create 2022-06-06 10:23
 */
@Configuration
public class EnumConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new CodeName2EnumConverterFactory());
    }
}
