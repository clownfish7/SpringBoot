package com.clownfish7.springbootactiviti7.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author You
 * @create 2020-04-20 23:06
 */
@RestController
public class HaloController {

    @RequestMapping("/halo")
    public String halo() {
        return "halo";
    }

    @RequestMapping("/halo/haha")
    public String halohaha() {
        return "halo";
    }

    @RequestMapping("/xixi")
    public String xixi() {
        return "xixi";
    }
}
