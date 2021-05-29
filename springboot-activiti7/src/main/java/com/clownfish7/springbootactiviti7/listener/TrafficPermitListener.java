package com.clownfish7.springbootactiviti7.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @author yzy
 * @classname TrafficPermitListener
 * @description TODO
 * @create 2020-04-21 10:12 AM
 */
public class TrafficPermitListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("------ doingSomething ------");
        System.out.println("---------- 审批通过 ----------");
    }
}
