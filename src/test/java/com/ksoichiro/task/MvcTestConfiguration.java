package com.ksoichiro.task;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration
@TestPropertySource(properties = "application.security.enabled: true")
public @interface MvcTestConfiguration {
}
