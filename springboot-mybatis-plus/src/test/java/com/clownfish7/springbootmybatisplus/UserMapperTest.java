package com.clownfish7.springbootmybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clownfish7.springbootmybatisplus.dao.UserMapper;
import com.clownfish7.springbootmybatisplus.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jnlp.UnavailableServiceException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
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
        int result = userMapper.insert(user);
        System.out.println("数据库受影响行数 -> " + result);
        System.out.println("插入id -> " + user.getId());
    }

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(1L);
        user.setAge(99);
        int result = userMapper.updateById(user);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setAge(99);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "yzy");

        int result = userMapper.update(user, wrapper);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testUpdate2() {


        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("password", "cba")
                .eq("user_name", "yzy");

        int result = userMapper.update(null, wrapper);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testDeleteById() {
        int result = userMapper.deleteById(7L);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testDeleteByMap() {

        Map<String, Object> map = new HashMap<>();
        map.put("user_name", "张三");
        map.put("password", "111");
        int result = userMapper.deleteByMap(map);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testDelete() {

        // 方法一
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_name", "yzy");
//        wrapper.eq("password", "yzy");

        // 方法二
        User user = new User();
        user.setAge(99);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

        int result = userMapper.delete(wrapper);
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(1L, 2L));
        System.out.println("数据库受影响行数 -> " + result);
    }

    @Test
    public void testSelectBatchIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectOne() {
        User user = new User();
        user.setAge(99);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        User u = userMapper.selectOne(wrapper);
        System.out.println("user -> " + u);
    }

    @Test
    public void testSelectCount() {
        User user = new User();
        user.setAge(99);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        Integer integer = userMapper.selectCount(wrapper);
        System.out.println("count -> " + integer);
    }

    @Test
    public void testSelectList() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("email", "@");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,1);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("email", "@");
        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
        System.out.println("数据总条数: " + userIPage.getTotal());
        System.out.println("数据总页数: " + userIPage.getPages());
        System.out.println("当前页数数: " + userIPage.getCurrent());

        List<User> records = userIPage.getRecords();
        records.forEach(System.out::println);

    }

    @Test
    public void testAllEq() {

        Map<String, Object> map = new HashMap<>();
        map.put("age", 99);
        map.put("password", "123456");
        map.put("name", null);

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        // SELECT id,email AS mail,name,user_name,age FROM tb_user WHERE (password = ? AND name IS NULL AND age = ?)
//        wrapper.allEq(map);

        // SELECT id,email AS mail,name,user_name,age FROM tb_user WHERE (password = ? AND age = ?)
//        wrapper.allEq(map,false);

        // SELECT id,email AS mail,name,user_name,age FROM tb_user WHERE (password = ?)
        wrapper.allEq((k,v)->(k.equals("password")),map);
        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }
}
