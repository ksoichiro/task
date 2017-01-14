package com.ksoichiro.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    @ConditionalOnMissingBean(ErrorAttributes.class)
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }
}
