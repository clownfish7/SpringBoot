package com.clownfish7.springbootcache.controller;

import com.clownfish7.springbootcache.pojo.Employee;
import com.clownfish7.springbootcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author You
 * @create 2019-07-06 21:57
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    private Employee getEmpById(@PathVariable Integer id) {
        return employeeService.getEmp(id);
    }

    @PutMapping
    public Employee update(Employee employee) {
        Employee emp = employeeService.updateEmp(employee);
        return emp;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        employeeService.deleteEmp(id);
        return "success";
    }
}
