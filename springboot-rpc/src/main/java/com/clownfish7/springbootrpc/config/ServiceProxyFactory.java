package com.clownfish7.springbootrpc.config;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author You
 * @create 2021-05-29 15:48
 */
public class ServiceProxyFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceType;

    public ServiceProxyFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        return (T)Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class[]{interfaceType},
                (proxy, method, args) -> {
                    System.out.println("invoke:" + method.getName() + " " + args);
                    method.setAccessible(true);
                    Class<?> returnType = method.getReturnType();

                    return 1;
                });
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
