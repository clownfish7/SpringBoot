package com.clownfish7.springbootdubboproviderticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author You
 * @create 2019-07-14 15:55
 */
@Component
@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "<伍佰演唱会>";
    }
}
