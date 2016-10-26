package com.ksoichiro.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GitProperties gitProperties;

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        VersionResourceResolver versionResolver = new VersionResourceResolver()
            .addContentVersionStrategy("/css/**", "/js/**")
            .addVersionStrategy(new PrefixAndFixedVersionStrategy("lib/", gitProperties.getCommitId()), "/lib/**");
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:static/")
            .setCachePeriod(null)
            .resourceChain(true)
            .addResolver(versionResolver);
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
