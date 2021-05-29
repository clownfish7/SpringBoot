package com.clownfish7.springbootactiviti7.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yzy
 * @classname No1Listener
 * @description TODO
 * @create 2020-04-22 9:30 AM
 */
@Service("No1Listener")
public class No1Listener implements TaskListener {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("start no 1");
        System.out.println(delegateTask.getProcessInstanceId());
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        System.out.println(forEntity);
    }

}
