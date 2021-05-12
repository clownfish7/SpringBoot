package com.clownfish7.springbootactiviti7;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yzy
 * @classname TrafficPermitTest
 * @description TODO
 * @create 2020-04-20 4:02 PM
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TesttTest {

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
    @Transactional
    @Rollback(false)
    public void testProcessInstenceStart() {
        SysDict sysDict = new SysDict();
        sysDict.setId("xxxxxxxxxxxxxxxxxx");
        sysDict.setCode("");
        sysDict.setValue("");
        sysDict.setName("");
        sysDict.setSort((short)0);
        sysDict.setStatus((short)0);
        sysDict.setDescription("");
        sysDict.setCreateBy("");
        sysDict.setCreateTime(new Date());
        sysDict.setUpdateBy("");
        sysDict.setUpdateTime(new Date());
        sysDict.setRemark("");

//        ProcessInstance trafficPermit = runtimeService.startProcessInstanceByKey("hamapi");
        int i = 1 / 0;
//        System.out.println("开启一个通行证申请实例：" + trafficPermit.getProcessInstanceId());
    }

    @Test
    public void test1() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processDefinitionKey("test").taskAssignee("p1").singleResult();
        System.out.println(task.getId());
        System.out.println(task.getExecutionId());
        System.out.println(task.getProcessInstanceId());
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("a", 1);
        variables.put("b", 1);
        variables.put("c", 1);
        variables.put("d", 1);
        variables.put("e", 1);
        runtimeService.setVariables(task.getProcessInstanceId(), variables);
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));
        taskService.setAssignee(task.getId(), null);
        taskService.claim(task.getId(), "yzy");
        taskService.complete(task.getId());
    }

    @Test
    public void test21() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processDefinitionKey("test").taskAssignee("p21").singleResult();

        HashMap<String, Object> variables = new HashMap<>();
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));
//        runtimeService.removeVariables(task.getExecutionId(), new ArrayList<>(taskService.getVariables(task.getId()).keySet()));
//        taskService.removeVariables(task.getExecutionId(), new ArrayList<>(taskService.getVariables(task.getId()).keySet()));
        variables.putAll(runtimeService.getVariables(task.getProcessInstanceId()));
        variables.put("b", 2);
//        runtimeService.setVariables(task.getProcessInstanceId(), variables);
        taskService.setVariables(task.getId(), variables);
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));
        taskService.complete(task.getId());
    }

    @Test
    public void test22() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processDefinitionKey("test").taskAssignee("p22").singleResult();
        System.out.println(task.getId());
        System.out.println(task.getExecutionId());
        System.out.println(task.getProcessInstanceId());
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));
//        runtimeService.removeVariables(task.getExecutionId(), new ArrayList<>(taskService.getVariables(task.getId()).keySet()));
//        taskService.removeVariables(task.getExecutionId(), new ArrayList<>(taskService.getVariables(task.getId()).keySet()));
        HashMap<String, Object> variables = new HashMap<>();
        variables.putAll(runtimeService.getVariables(task.getProcessInstanceId()));
        variables.put("c", 2);
//        runtimeService.setVariables(task.getProcessInstanceId(), variables);
        taskService.setVariables(task.getId(), variables);
        variables = new HashMap<>();
        variables.put("a", 2);
//        runtimeService.setVariables(task.getProcessInstanceId(), variables);
        taskService.setVariables(task.getId(), variables);
        variables = new HashMap<>();
        variables.put("e", 2);
//        runtimeService.setVariables(task.getProcessInstanceId(), variables);
        taskService.setVariables(task.getId(), variables);
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));
        taskService.complete(task.getId());
    }

    @Test
    public void test3() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processDefinitionKey("test").taskAssignee("p3").singleResult();
        System.out.println(task.getId());
        System.out.println(task.getExecutionId());
        System.out.println(task.getProcessInstanceId());
        System.out.println(taskService.getVariables(task.getId()));
        System.out.println(runtimeService.getVariables(task.getExecutionId()));
        System.out.println(runtimeService.getVariables(task.getProcessInstanceId()));

        taskService.complete(task.getId());
    }

    @Test
    public void testHistory() {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> list = historicActivityInstanceQuery
                .processInstanceId("5a705053-83c4-11ea-84c1-005056c00001")
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
        list.forEach(act -> {
            System.out.println(act);
        });
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskName("1").list().forEach(System.out::println);
    }

    @Test
    public void testCommentMessage() {
        taskService.createTaskQuery().list().forEach(task -> {
//            taskService.addComment(task.getId(), task.getProcessInstanceId(), "lalala");
//            taskService.addComment(task.getId(), task.getProcessInstanceId(), "type","lalala");
            taskService.getTaskComments(task.getId()).forEach(comment -> System.out.println(task.getId() + ":" + comment.getFullMessage() + comment.getType()));
        });
    }

    @Test
    public void matash() {
        List<Comment> taskComments = taskService.getTaskComments("083e1b7e-8441-11ea-9650-005056c00001");
        List<Comment> type = taskService.getCommentsByType("type");
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments("083a4aea-8441-11ea-9650-005056c00001");
        System.out.println();
    }
}

