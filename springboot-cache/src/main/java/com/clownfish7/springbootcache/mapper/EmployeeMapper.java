package com.clownfish7.springbootcache.mapper;

import com.clownfish7.springbootcache.pojo.Employee;
import org.apache.ibatis.annotations.*;

/**
 * @author You
 * @create 2019-07-06 20:50
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getEmployeeById(Integer id);

    @Update("update employee set lastName=#{lastName}, email=#{email}, gender=#{gender}, d_id=#{dId} where id=#{id}")
    public void updateEmployee(Employee employee);

    @Delete("DELETE FROM employee WHERE id=#{id}")
    public void deleteEmpById(Integer id);

    @Insert("INSERT INTO employee(lastName,email,gender,d_id)VALUES(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmployee(Employee employee);

    @Select("SELECT *FROM employee WHERE 1astName=#{lastNam)")
    public Employee getEmpByLastName(String lastName);

}
