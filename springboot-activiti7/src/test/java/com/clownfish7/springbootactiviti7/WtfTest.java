package com.clownfish7.springbootactiviti7;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzy
 * @classname WtfTest
 * @description TODO
 * @create 2020-04-24 9:25 AM
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WtfTest {

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
    public void testCreateProcessInstence() {
        // 启动一个 key 为 trafficPermit 的流程实例, businessKey 可填 通行证 id
        ProcessInstance trafficPermit = runtimeService
                .startProcessInstanceByKey("trafficPermit", "businessKey", variablesNeedJdAndPolice());
        System.out.println("开启一个通行证申请实例：" + trafficPermit.getProcessInstanceId());
        System.out.println("processInstence: " + runtimeService.getVariables(trafficPermit.getProcessInstanceId()));
    }

    @Test
    public void testTrafficPermitApply() {
        // 企业申请通行证 可页面点击申请的时候和上面的单元测试一起直接创建实例并完成 task
        List<Task> taskList = taskService.createTaskQuery()
                // 根据流程图上申请通行证这个 userTask 的名字查询正在运行的 task
                .taskName("申请通行证")
                // task 所属流程实例 bussinessKey 为 “businessKey” 的
                .processInstanceBusinessKey("businessKey")
                // task 所属流程实例的 id 为 "" 的
//                .processInstanceId("")
                // 根据 task 创建时间排序
                .orderByTaskCreateTime().asc()
                // 获取所有 task 列表
                .list();

        taskList.forEach(task -> {
            // 设置 task 执行人为企业申请通行证时登录账号的 id
            taskService.setAssignee(task.getId(), "companyId");
            // 设置 task 拥有人为企业申请通行证时登录账号的 id
            taskService.setOwner(task.getId(), "companyId");
            // 完成任务
            taskService.complete(task.getId());
        });
    }

    @Test
    public void testJudgeWithJd()   {
        List<Task> taskList = taskService.createTaskQuery()
                .taskName("协会审批")
                // 根据 task 创建时间排序
                .orderByTaskCreateTime().asc()
                // 获取所有 task 列表
                .list();

        taskList.forEach(task -> {
            // 设置 task 执行人为协会登录账号的 id
            taskService.setAssignee(task.getId(), "jd");
            taskService.setOwner(task.getId(), "jd");
            // 添加批注信息  - taskId processInstenceId 批注类型 内容
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "type", "message");
            taskService.complete(task.getId());
            HashMap<String, Object> variables = new HashMap<>();
            // 建德协会审批通过
            variables.put("jd", 1);
            // 是否需要还需要建德协会审批
            variables.put("needJd", 0);
            // 覆盖原有同名流程变量
            taskService.setVariables(task.getId(), variables);
            // 完成任务
            taskService.complete(task.getId());
        });
    }

    @Test
    public void testJudgeWithPoliceABC() {
        // 中队1 2 3 同理
        List<Task> taskList = taskService.createTaskQuery()
                .taskName("中队1")
                // 根据 task 创建时间排序
                .orderByTaskCreateTime().asc()
                // 获取所有 task 列表
                .list();

        taskList.forEach(task -> {
            // 设置 task 执行人为中队1登录账号的 id
            taskService.setAssignee(task.getId(), "policeA");
            taskService.setOwner(task.getId(), "policeA");
            // 添加批注信息  - taskId processInstenceId 批注类型 内容
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "type", "message");
            taskService.complete(task.getId());
            HashMap<String, Object> variables = new HashMap<>(runtimeService.getVariables(task.getProcessInstanceId()));
            // 交警中队1审批通过
            variables.put("policeA", 1);
            // 是否需要还需要交警中队1审批
            variables.put("needA", 1);
            // 覆盖原有同名流程变量
            taskService.setVariables(task.getId(), variables);
            // 完成任务
            taskService.complete(task.getId());
        });
    }

    @Test
    public void testProcessInstenceQuery() {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> list = historicActivityInstanceQuery
                .processInstanceId("5a705053-83c4-11ea-84c1-005056c00001")
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
        list.forEach(System.out::println);
    }

    @Test
    public void testCommentQuery() {
        List<Comment> taskComments = taskService.getTaskComments("083e1b7e-8441-11ea-9650-005056c00001");
        List<Comment> type = taskService.getCommentsByType("type");
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments("083a4aea-8441-11ea-9650-005056c00001");
    }

    private Map<String, Object> variablesNeedJdAndPolice() {
        Map<String, Object> variables = new HashMap<>();
        // 建德协会审批结果 0 - 未通过  1 - 通过
        variables.put("jd", 0);
        // 需要建德协会审批 0 - 不需要  1 - 需要
        variables.put("needJd", 1);
        // 需要交警中队1审批 0 - 不需要  1 - 需要
        variables.put("needA", 1);
        // 需要交警中队2审批 0 - 不需要  1 - 需要
        variables.put("needB", 1);
        // 需要交警中队3审批 0 - 不需要  1 - 需要
        variables.put("needC", 1);
        // 交警中队1审批结果 0 - 未通过  1 - 通过
        variables.put("policeA", 0);
        // 交警中队2审批结果 0 - 未通过  1 - 通过
        variables.put("policeB", 0);
        // 交警中队3审批结果 0 - 未通过  1 - 通过
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
