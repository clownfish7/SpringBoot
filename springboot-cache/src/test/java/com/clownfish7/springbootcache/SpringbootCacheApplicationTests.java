package com.clownfish7.springbootcache;

import com.clownfish7.springbootcache.mapper.EmployeeMapper;
import com.clownfish7.springbootcache.pojo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;

    //k-v 都是对象的
    @Autowired
    private RedisTemplate redisTemplate;

    //k-v 都是字符串的
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Employee> empRedisTemplate;

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.getEmployeeById(1);
        System.out.println(employee);
    }

    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、zSet（有序集合）
     * stringRedisTemplate.opsForValue();   String（字符串）
     * stringRedisTemplate.opsForList();    List（列表）
     * stringRedisTemplate.opsForSet();     Set（集合）
     * stringRedisTemplate.opsForHash();    Hash（散列）
     * stringRedisTemplate.opsForZSet();    zSet（有序集合）
     */
    @Test
    public void testStringRedis() {
        //给redis中保存数据
//        stringRedisTemplate.opsForValue().append("msg", "hello_redis");

//        String msg = stringRedisTemplate.opsForValue().get("msg");
//        System.out.println(msg);

//        stringRedisTemplate.opsForList().leftPush("list", "1");
//        stringRedisTemplate.opsForList().leftPush("list", "2");
    }

    @Test
    public void testRedis() {
        //保存对象
        Employee employee = employeeMapper.getEmployeeById(1);
        //默认保存对象是同jdk序列化
        //1、将数据以json的方式保存
        //（1）自己将对象转为json
        //（2）redis Template默认的序列化规则
        redisTemplate.opsForValue().set("emp-01", employee);
    }

    @Test
    public void testEmpRedis() {
        //保存对象
        Employee employee = employeeMapper.getEmployeeById(1);
        empRedisTemplate.opsForValue().set("emp-01", employee);
    }
}
