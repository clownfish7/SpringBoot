package com.clownfish7.springbootshardingjdbc.controller;

import com.clownfish7.springbootshardingjdbc.dao.DbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author yzy
 * @classname HolaController
 * @description TODO
 * @create 2019-12-19 1:26 PM
 */
@RestController
public class HolaController {

    @Autowired
    private DbDao dbDao;

    @RequestMapping("/hola")
    public String hole() {
        for (int i = 0; i < 50; i++) {
            dbDao.insert(new BigDecimal(i), (long) i, ""+i);
        }
        return "ok";
    }
}
