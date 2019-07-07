package com.clownfish7.springbootamqp.service;

import com.clownfish7.springbootamqp.pojo.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author You
 * @create 2019-07-07 17:34
 */
@Service
public class BookService {
    @RabbitListener(queues = {"clownfish.news"})
    public void receive(Book book) {
        System.out.println(book);
    }

    @RabbitListener(queues = {"clownfish"})
    public void receiveMsg(Message message) {
        System.out.println(message.getMessageProperties());
        System.out.println(message.getBody());
    }
}
