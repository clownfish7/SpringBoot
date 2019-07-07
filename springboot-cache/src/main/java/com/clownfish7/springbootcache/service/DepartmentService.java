package com.clownfish7.springbootcache.service;

import com.clownfish7.springbootcache.mapper.DepartmentMapper;
import com.clownfish7.springbootcache.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author You
 * @create 2019-07-07 13:27
 */
@Service
@CacheConfig(cacheNames = "dept")
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable
    public Department getDeptById(Integer id) {
        System.out.println("find dept with id="+id);

        Cache cache = cacheManager.getCache("dept");
        cache.put("11", "22");

        return departmentMapper.getDeptById(id);
    }
}
