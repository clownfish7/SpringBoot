package com.clownfish7.springbootsomething.annotation;

import java.lang.annotation.*;

/**
 * @author yzy
 * @classname Camel
 * @description Map-key camel2underline & underline2camel
 * @create 2019-10-16 9:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Camel {
    String value() default "";
}
