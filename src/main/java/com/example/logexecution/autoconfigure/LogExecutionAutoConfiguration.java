package com.example.logexecution.autoconfigure;

import com.example.logexecution.aspect.LogExecutionTimeAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(
        prefix = "log.execution",
        name = "enabled",
        havingValue = "true")

public class LogExecutionAutoConfiguration {

    @Bean
    public LogExecutionTimeAspect logExecutionTimeAspectM() {
        return new LogExecutionTimeAspect();
    }

}
