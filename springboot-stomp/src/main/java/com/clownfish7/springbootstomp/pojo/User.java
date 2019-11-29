package com.clownfish7.springbootstomp.pojo;

import java.security.Principal;

/**
 * @author yzy
 * @classname User
 * @description TODO
 * @create 2019-11-28 5:47 PM
 */
public class User implements Principal {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
