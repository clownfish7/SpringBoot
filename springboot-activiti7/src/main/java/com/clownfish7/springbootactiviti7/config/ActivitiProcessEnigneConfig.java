package com.clownfish7.springbootactiviti7.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author yzy
 * @classname ActivitiProcessEnigneConfig
 * @description TODO
 * @create 2020-08-26 16:16
 */
@Component
public class ActivitiProcessEnigneConfig implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        springProcessEngineConfiguration.setDatabaseTablePrefix("pony_activiti.");
        springProcessEngineConfiguration.setTablePrefixIsSchema(true);
        springProcessEngineConfiguration.setDatabaseSchemaUpdate("donothing");
    }
}
