package com.clownfish7.springbootskywalking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clownfish7.springbootskywalking.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author You
 * @create 2020-02-11 21:30
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
