package com.clownfish7.springbootmybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clownfish7.springbootmybatisplus.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author You
 * @create 2019-09-28 17:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveRecordTest {

    @Test
    public void testSelectById() {
        User user = new User();
        user.setUserName("yzy");
        user.setId(5L);
        User user1 = user.selectById();
        System.out.println(user1);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("clownfish");
        user.setUserName("yzy");
        user.setPassword("abc");
        user.setAge(18);
        user.setMail("clownfish@");
        user.setAddress("aaa");
        boolean insert = user.insert();
        System.out.println("数据库受影响行数 -> " + insert);
        System.out.println("插入id -> " + user.getId());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(8L);
        user.setAge(19);
        boolean insert = user.updateById();
        System.out.println("数据库受影响行数 -> " + insert);
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        boolean insert = user.deleteById();
        System.out.println("数据库受影响行数 -> " + insert);
    }

    @Test
    public void testSelect() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age", 11);
        List<User> users = user.selectList(wrapper);
        for (User user1 : users) {
            System.out.println(user1);
        }
    }

    @Test
    public void testUpdateAll() {
        User user = new User();
        user.setAge(1);
        boolean result = user.update(null);
        System.out.println("result -> " + result);
    }
}
