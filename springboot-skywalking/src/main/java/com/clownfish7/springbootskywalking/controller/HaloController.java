package com.clownfish7.springbootskywalking.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author You
 * @create 2020-02-11 20:10
 */
@RestController
@RequestMapping("/halo")
public class HaloController {

    @RequestMapping()
    public String halo() {
        return "halo!!!";
    }

    @RequestMapping("/{name}")
    public String halo(@PathVariable String name) {
        return "halo " + name + " !!!";
    }
}
