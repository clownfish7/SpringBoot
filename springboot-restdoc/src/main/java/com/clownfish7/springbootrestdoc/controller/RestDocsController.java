package com.clownfish7.springbootrestdoc.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzy
 * @classname RestDocsController
 * @description TODO
 * @create 2020-08-20 17:18
 */
@RestController
@RequestMapping("/restDoc")
public class RestDocsController {

    @GetMapping("/get")
    public Map<String, Object> restDocsGet() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("test", "get");
        result.put("code", 200);
        return result;
    }

    @GetMapping("/get/{param1}")
    public Map<String, Object> restDocsGetPath(@PathVariable String param1) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("test", "getPath");
        result.put("code", param1);
        return result;
    }

    @PostMapping("/post")
    public Map<String, Object> restDocsPost(@RequestBody Map<String, Object> param) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("test", "post");
        result.put("code", 200);
        return result;
    }
}
