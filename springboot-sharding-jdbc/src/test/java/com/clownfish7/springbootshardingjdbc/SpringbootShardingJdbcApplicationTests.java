package com.clownfish7.springbootshardingjdbc;

import com.clownfish7.springbootshardingjdbc.dao.DbDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SpringBootTest()
class SpringbootShardingJdbcApplicationTests {

    @Autowired
    private DbDao dbDao;

    @Test
    void contextLoads() {
        for (int i = 0; i < 50; i++) {
            dbDao.insert(new BigDecimal(i), (long) i, "" + i);
        }
    }

    @Test
    void select() {
        List<Map<String, Object>> select = dbDao.select();
        select.forEach(System.out::println);
    }

    @Test
    void insertConfig() {
        Integer integer = dbDao.insertConfig(2);
    }

    @Test
    void selectConfig() {
        List<Map<String, Object>> select = dbDao.selectConfig();
        select.forEach(System.out::println);
    }
}
