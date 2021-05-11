package com.clownfish7.springbootskywalking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author You
 * @create 2020-02-11 21:30
 */
@Data
@TableName("user")
public class User extends Model<User> {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
