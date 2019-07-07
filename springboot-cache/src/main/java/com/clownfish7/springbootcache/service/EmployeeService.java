package com.clownfish7.springbootcache.service;

import com.clownfish7.springbootcache.mapper.EmployeeMapper;
import com.clownfish7.springbootcache.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author You
 * @create 2019-07-06 21:53
 */
@CacheConfig(cacheNames = "emp") //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 将方法的运行结果进行缓存; 以后再要相同的数据，直接从缓存中获取，不用调用方法;
     * CacheManager管理多个cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字;
     * 几个属性:
     * cacheNames/value: 指定缓存组件的名字;
     * key:缓存数据使用的key;可以用它来指定。默认是使用方法参数的值  1-方法的返回值
     * 编写SpEL; #id; 参数id的值 #a0 #p0 #root.args[0]
     * keyGenerator:key的生成器;可以自己指定key的生成器的组件id
     * key/keyGenerator:二选一使用
     * cacheManager:指定缓存管理器;或者cacheResolver指定获取解析器
     * condition:指定符合条件的情况下才缓存;
     * unLess:  否定缓存;当unless指定的条件为true，方法的返回值就不会被缓存;可以获取到结果进行判断
     * sync: 是否使用异步模式
     *
     * @param id
     * @return 原理:
     * 1、自动配置类;CacheAutoconfiguration
     * <p>
     * 2、缓存的配置类
     * org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.JCachecacheConfiguration
     * org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.Rediscacheconfiguration
     * org.springframework.boot.autoconfigure.cache.CaffeinecacheConfiguration
     * org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     * <p>
     * 3、哪个配置类默认生效:Simplecacheconfiguration;
     * <p>
     * 4、给容器中注册了一个CacheManager:ConcurrentMapCacheManager
     * <p>
     * 5、可以获取和创建ConcurrentMapCache类型的缓存组件;他的作用将数据保存在ConcurrentNap中;
     * <p>
     * 运行流程:
     * @Cacheable: 1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取;
     * （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建。
     * 2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数;key是按照某种策略生成的;默认是使用keyGenerator生成的，默认使用SimplekeyGenerator生成key
     * SimplekeyGenerator生成key的默认策略;
     * 如果没有参数;key=new Simplekey（）;
     * 如果有一个参数:key=参数的值
     * 如果有多个参数:key=new simpleKey（params）;
     * 3、没有查到缓存就调用目标方法;
     * 4、将目标方法返回的结果，放进缓存中
     * @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，如果没有就运行方法将结果放入缓存，下次调用就可以直接使用缓存中的数据。 核心：
     * 1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     * 2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
     * <p>
     * 几个属性:
     * *  cacheNames/value: 指定缓存组件的名字; 将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存
     * <p>
     * *  key:缓存数据使用的key;可以用它来指定。默认是使用方法参数的值  1-方法的返回值
     * *          编写SpEL; #id; 参数id的值 #a0 #p0 #root.args[0]
     * getEmp[1] key="#root.methodName+'['+#id+']'"
     * <p>
     * *  keyGenerator:key的生成器;可以自己指定key的生成器的组件id
     * *          key/keyGenerator:二选一使用
     * <p>
     * *  cacheManager:指定缓存管理器;或者cacheResolver指定获取解析器
     * <p>
     * *  condition:指定符合条件的情况下才缓存;
     * condition = "#a0==1" 第一个参数id=1的才缓存 不符合条件的不缓存
     * <p>
     * *  unLess:  否定缓存;当unless指定的条件为true，方法的返回值就不会被缓存;可以获取到结果进行判断
     * unless = "#a0==2" 如果a0 第一个参数id=2 不缓存
     * <p>
     * *  sync: 是否使用异步模式
     * 异步默认=false  启动异步时不支持unless
     */
    @Cacheable(cacheNames = {"emp"}, key = "#a0"/*keyGenerator = "myKeyGenerator"*/, condition = "#a0==1", unless = "#a0==2")
    public Employee getEmp(Integer id) {
        System.out.println("查询id为: " + id + " 的员工");
        Employee emp = employeeMapper.getEmployeeById(id);
        return emp;
    }

    /**
     * @CachePut：既调用方法，又更新缓存数据 修改了数据库的某个数据，同时更新缓存；
     * <p>
     * 运行时机；
     * 1、先调用目标方法
     * 2、将目标方法的结果缓存起来
     * <p>
     * 测试步骤：
     * 1、查询1号员工；查到的结果会放在缓存中；key：1 value:LastName：|
     * 2、以后查询还是之前的结果
     * 3、更新1号员工：【lastName:zhangsan；gender:1】
     * 4、查询1号员工？应该是更新后的员工；为什么是没更新前的？ （插入缓存key要一致）
     * key="#employee.id”：使用传入的参数的员工id；
     * key="#result.id”：使用返回后的id
     * @Cacheable的key是不能用 应为先查缓存 result是null
     */
    @CachePut(value = "emp", key = "#a0.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新: " + employee + " 员工");
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    /**
     * @CacheEvict：缓存清除 key：指定要清除的效据
     * allEntries = true        默认false 清空emp下所有缓存
     * beforeInvocation = true  默认false 在方法执行后删除缓存，出错不删除缓存
     * 是true表示在方法执行前删除缓存不管出不出错
     */
    @CacheEvict(value = "emp", key = "#id", allEntries = true, beforeInvocation = true)
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
//        employeeMapper.deleteEmpById(id);
        int i = 10 / 0;
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = "emp", key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp", key = "#result.id"),
                    @CachePut(value = "emp", key = "#result.email"),
                    @CachePut(value = "emp", key = "#result.gender")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
