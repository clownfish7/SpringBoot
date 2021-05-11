package com.clownfish7.springbootskywalking.controller;

import com.clownfish7.springbootskywalking.dao.UserDao;
import com.clownfish7.springbootskywalking.pojo.User;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author You
 * @create 2020-02-11 21:32
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @RequestMapping("/")
    public List<User> users() {
        List<User> users = userDao.selectList(null);
        redisTemplate.opsForValue().set("kk1","vv1");
        String o = (String) redisTemplate.opsForValue().get("kk1");
        return users;
    }

    @RequestMapping("/rabb")
    public String rabb() {
        createExchange();
        rabbitTemplate.convertAndSend("amqpAdmin.exchange","amqp.uu","amqpadmin.queue");
        return "xixi";
    }

    public void createExchange() {
        amqpAdmin.declareExchange(new TopicExchange("amqpAdmin.exchange"));
        System.out.println("创建完成");
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpAdmin.exchange","amqp.uu",null));

    }


}
