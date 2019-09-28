package com.clownfish7.springbootmybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author You
 * @create 2019-09-28 15:13
 */
@Data
@TableName("tb_user")
public class User extends Model<User> {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String userName;
    @TableField(select = false)
    private String password;
    private Integer age;
    @TableField(value = "email")
    private String mail;

    @TableField(exist = false)
    private String address;
}
