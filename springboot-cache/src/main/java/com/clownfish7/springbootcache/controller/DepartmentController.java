package com.clownfish7.springbootcache.controller;

import com.clownfish7.springbootcache.pojo.Department;
import com.clownfish7.springbootcache.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author You
 * @create 2019-07-07 13:29
 */
@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{id}")
    public Department getDeptById(@PathVariable Integer id) {
        return departmentService.getDeptById(id);
    }
}
