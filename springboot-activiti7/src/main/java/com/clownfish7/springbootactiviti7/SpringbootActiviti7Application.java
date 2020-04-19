package com.clownfish7.springbootactiviti7;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootActiviti7Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActiviti7Application.class, args);
    }
    /**
     * RepositoryService
     * 通过流程引擎来取得
     * @param processEngine
     * @return
     */
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }
}
