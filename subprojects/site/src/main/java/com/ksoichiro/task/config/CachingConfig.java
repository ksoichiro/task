package com.ksoichiro.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "application.caching.enabled", havingValue = "true", matchIfMissing = true)
@EnableCaching
public class CachingConfig {
}
