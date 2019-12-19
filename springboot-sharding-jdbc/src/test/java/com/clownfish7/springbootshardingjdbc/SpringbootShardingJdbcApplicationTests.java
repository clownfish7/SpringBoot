package com.clownfish7.springbootshardingjdbc;

import com.clownfish7.springbootshardingjdbc.dao.DbDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest()
class SpringbootShardingJdbcApplicationTests {

    @Autowired
    private DbDao dbDao;

    @Test
    void contextLoads() {
        for (int i = 0; i < 50; i++) {
            dbDao.insert(new BigDecimal(i), (long) i, ""+i);
        }
    }

}
