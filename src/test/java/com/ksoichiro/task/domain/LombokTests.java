package com.ksoichiro.task.domain;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public abstract class LombokTests {
    public void test(Class<? extends Annotation> annotationClass, String packageName) throws Exception {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        if (annotationClass != null) {
            provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        }
        for (BeanDefinition beanDef : provider.findCandidateComponents(packageName)) {
            Class<?> c = Class.forName(beanDef.getBeanClassName());
            Object entity = c.newInstance();
            for (Field field : entity.getClass().getDeclaredFields()) {
                Method getter = ReflectionUtils.findMethod(c, "get" + capitalize(field.getName()));
                Method setter = ReflectionUtils.findMethod(c, "set" + capitalize(field.getName()), field.getType());
                if (getter == null || setter == null) {
                    continue;
                }
                getter.invoke(entity);
                setter.invoke(entity, new Object[]{null});
            }
            Method canEqual = ReflectionUtils.findMethod(c, "canEqual", Object.class);
            if (canEqual != null) {
                assertThat(canEqual.invoke(entity, entity), is(true));
            }
            assertThat(entity.equals(entity), is(true));
            assertThat(entity.equals(null), is(false));
            assertThat(entity.equals(1), is(false));
            entity.equals(c.newInstance());
            entity.hashCode();
            entity.toString();
        }
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
