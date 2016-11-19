package com.ksoichiro.task.form;

import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TaskCreateFormTests {
    @AllArgsConstructor
    static class Fixture {
        private TaskCreateForm form;
        private boolean expectedError;
        private Map<String, String> errors;
    }

    @DataPoints
    public static final Fixture[] FIXTURES = new Fixture[]{
        new Fixture(new TaskCreateForm(null, null, null), true, new HashMap<String, String>() {
            {
                put("name", "may not be empty");
                put("status", "may not be null");
            }
        }),
    };

    private Validator validator;

    @Before
    public void setUp() {
        validator = createValidator();
    }

    @Theory
    public void validate(Fixture fixture) {
        TaskCreateForm form = new TaskCreateForm();
        BeanUtils.copyProperties(fixture, form);
        BindException errors = new BindException(form, "form");
        ValidationUtils.invokeValidator(validator, form, errors);
        assertThat(errors.hasErrors(), is(fixture.expectedError));
        assertThat(errors.getFieldErrors().size(), is(fixture.errors.size()));
        fixture.errors.keySet().forEach(e -> {
            assertThat(errors.hasFieldErrors(e), is(true));
            assertThat(errors.getFieldError(e).getDefaultMessage(), is(fixture.errors.get(e)));
        });
    }

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
