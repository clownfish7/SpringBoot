package com.clownfish7.springbootcache.mapper;

import com.clownfish7.springbootcache.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author You
 * @create 2019-07-06 20:50
 */
@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    public Department getDeptById(Integer id);
}
