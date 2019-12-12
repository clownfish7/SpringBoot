package com.clownfish7.springbootwebflux.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yzy
 * @classname User
 * @description TODO
 * @create 2019-12-02 11:12 AM
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Id
    private Long id;
    private SexEnum sex;
//    @Field("user_name")
    private String userName;
    private String note;
}
