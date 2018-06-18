package com.ksoichiro.task.annotation;

import com.ksoichiro.task.App;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@TestPropertySource(properties = "application.security.enabled: true")
public @interface MvcTestConfiguration {
}
