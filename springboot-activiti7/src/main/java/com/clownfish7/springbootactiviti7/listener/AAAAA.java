package com.clownfish7.springbootactiviti7.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * @author yzy
 * @classname No1Listener
 * @description TODO
 * @create 2020-04-22 9:30 AM
 */
@Service("aaaaa")
public class AAAAA implements ExecutionListener {



    @Override
    public void notify(DelegateExecution execution) {
        System.out.println("start on aaaaa");
        System.out.println(execution.getProcessInstanceId());
    }

}
