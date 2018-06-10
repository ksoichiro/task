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
    private static final int CACHE_PERIOD = 100 * 24 * 60 * 60;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GitProps gitProperties;

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final VersionResourceResolver versionResolver = new VersionResourceResolver()
            .addContentVersionStrategy("/css/**", "/js/**")
            .addVersionStrategy(new PrefixAndFixedVersionStrategy("lib/", gitProperties.getCommitId()), "/lib/**");
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:static/")
            .setCachePeriod(CACHE_PERIOD)
            .resourceChain(true)
            .addResolver(versionResolver);
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
