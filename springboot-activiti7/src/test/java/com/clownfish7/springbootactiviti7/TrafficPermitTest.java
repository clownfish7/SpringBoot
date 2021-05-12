package com.clownfish7.springbootactiviti7;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzy
 * @classname TrafficPermitTest
 * @description TODO
 * @create 2020-04-20 4:02 PM
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrafficPermitTest {

    @Autowired
    private ProcessEngine processEngine;

    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private RepositoryService repositoryService;

    @BeforeAll
    public void init() {
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
        this.historyService = processEngine.getHistoryService();
        this.repositoryService = processEngine.getRepositoryService();
    }

    @Test
    public void testProcessDefinitionQuery() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        System.out.println(processDefinitionQuery.list());
    }

    @Test
    public void testProcessInstenceStart() {
        ProcessInstance trafficPermit = runtimeService.startProcessInstanceByKey("trafficPermit", variablesNeedJdAndPolice());
        System.out.println("开启一个通行证申请实例：" + trafficPermit.getProcessInstanceId());
        System.out.println("processInstence: " + runtimeService.getVariables(trafficPermit.getProcessInstanceId()));
    }

    @Test
    public void testTaskClaimComplateApply() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.singleResult();
        System.out.println("yzy申请：" + task.getProcessInstanceId());
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        taskService.claim(task.getId(), "yzy");
        taskService.setAssignee(task.getId(),"a");
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("jd", 0);
        variables.put("needJd", 1);
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
    }

    @Test
    public void testTaskClaimComplateJdPass() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.singleResult();
        String processInstanceId = task.getProcessInstanceId();
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        taskService.claim(task.getId(), "协会");
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("jd", 1);
        variables.put("needJd", 0);
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
    }

    @Test
    public void testTaskClaimComplatePoliceAPass() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskName("中队1审批").singleResult();
        taskService.claim(task.getId(), "中队1");
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        HashMap<String, Object> variables1 = new HashMap<>(runtimeService.getVariables(task.getProcessInstanceId()));
        variables1.put("policeA", 1);
        variables1.put("needA", 1);
        taskService.setVariables(task.getId(),variables1);
        taskService.complete(task.getId());
    }

    @Test
    public void testTaskClaimComplatePoliceBPass() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskName("中队2审批").singleResult();
        taskService.claim(task.getId(), "中队2");
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        HashMap<String, Object> variables2 = new HashMap<>(runtimeService.getVariables(task.getProcessInstanceId()));
        variables2.put("policeB", 1);
        variables2.put("needB", 1);
        taskService.setVariables(task.getId(),variables2);
        taskService.complete(task.getId());
    }

    @Test
    public void testTaskClaimComplatePoliceCPass() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskName("中队3审批").singleResult();
        taskService.claim(task.getId(), "中队3");
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        HashMap<String, Object> variables3 = new HashMap<>(runtimeService.getVariables(task.getProcessInstanceId()));
        variables3.put("policeC", 1);
        variables3.put("needC", 1);
        taskService.setVariables(task.getId(),variables3);
        taskService.complete(task.getId());
    }

    @Test
    public void testTaskClaimComplatePoliceCPassSetParam() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskName("中队3审批").singleResult();
        taskService.claim(task.getId(), "中队3");
        System.out.println("taskVariable   : " + taskService.getVariables(task.getId()));
        HashMap<String, Object> variables3 = new HashMap<>();
        variables3.put("policeC", 0);
        variables3.put("needC", 0);
        taskService.setVariables(task.getId(),variables3);
//        runtimeService.setVariables(task.getProcessInstanceId(), variables3);
    }

    private Map<String, Object> variablesNeedJdAndPolice() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("jd", 0);
        variables.put("needJd", 1);
        variables.put("needA", 1);
        variables.put("needB", 1);
        variables.put("needC", 1);
        variables.put("policeA", 0);
        variables.put("policeB", 0);
        variables.put("policeC", 0);
        return variables;
    }

    private Map<String, Object> variablesNeedPolice() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("jd", 0);
        variables.put("needJd", 0);
        variables.put("needA", 1);
        variables.put("needB", 1);
        variables.put("needC", 1);
        variables.put("policeA", 0);
        variables.put("policeB", 0);
        variables.put("policeC", 0);
        return variables;
    }

}
