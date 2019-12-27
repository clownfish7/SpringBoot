package com.clownfish7.springbootshardingjdbc.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yzy
 * @classname DbDao
 * @description TODO
 * @create 2019-12-19 10:31 AM
 */
@Mapper
public interface DbDao {

    @Insert(value = "insert into t_order(price,user_id,status) values(#{price},#{userId},#{status})")
    public Integer insert(@Param("price") BigDecimal price, @Param("userId") Long userId, @Param("status") String status);

//    @Select("select * from t_order where order_id in (414410998305259521,414410999941038080)")
    @Select("select * from t_order where order_id in (414410995109199873,414410998447865856)")
    public List<Map<String, Object>> select();

//    @Insert(value = "insert into t_config(key1,key2) values(#{v1},#{v2})")
//    public Integer insertConfig(@Param("v1") BigDecimal v1, @Param("v2") Integer v2);

    @Insert(value = "insert into t_config(key2) values(#{v2})")
    public Integer insertConfig(@Param("v2") Integer v2);
}
