package com.clownfish7.springbootamqp;

import com.clownfish7.springbootamqp.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAmqpApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
        //amqpAdmin.declareExchange(new TopicExchange("amqpAdmin.exchange"));
        //System.out.println("创建完成");

//        amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
        //创建绑定规则
//        amqpAdmin.declareBinding(new Binding("amqpadmin.queue",Binding.DestinationType.QUEUE,"amqpAdmin.exchange","amqp.uu",null));

    }

    /**
     * 1.单播(点对点)
     */
    @Test
    public void contextLoads() {
        //Message 需要自己构造；定义消息体和消息头
        //rabbitTemplate.send(exchange, routingKey, message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
        //rabbitTemplate.convertAndSend(exchage，routekey，object);
        Map<String,Object> map=new HashMap<>();
        map.put("mEg","这是第一个消息");
        map.put("data",Arrays.asList("helloworld",123,true));

        Book book = new Book("演员的自我修养","喜剧之王");
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","clownfish.news",book);
    }

    //接收数据；如何将数据转化为json格式？
    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("clownfish.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Book("三国演义","罗贯中"));
    }
}
