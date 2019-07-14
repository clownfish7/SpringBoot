package com.clownfish7.springbootdubboconsumeruser.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.clownfish7.springbootdubboproviderticket.service.TicketService;
import org.springframework.stereotype.Service;

/**
 * @author You
 * @create 2019-07-14 16:29
 */
@Service
public class UserService {

    @Reference
    private TicketService ticketService;

    public void hello() {
        String ticket = ticketService.getTicket();
        System.out.println(ticket);
    }

}
