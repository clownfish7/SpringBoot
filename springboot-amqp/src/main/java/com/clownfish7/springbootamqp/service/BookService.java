package com.clownfish7.springbootamqp.service;

import com.clownfish7.springbootamqp.pojo.Book;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public void receiveMsg(Message message, Channel channel, CorrelationData correlationData,
                           @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println(message.getMessageProperties());
        System.out.println(message.getBody());
        channel.basicAck(tag, false);
        channel.basicNack(tag, false, false);
    }
}
