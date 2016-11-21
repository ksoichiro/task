package com.ksoichiro.task.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public abstract class AbstractFormTests<F> {
    @AllArgsConstructor
    @Data
    static class Fixture<F> {
        private F form;
        private boolean expectedError;
        private Map<String, String> errors;
    }

    protected Validator validator;

    @Before
    public void setUp() {
        validator = createValidator();
    }

    @Test
    public void formEquals() throws Exception {
        // Just for increasing test coverage
        F form = getFormClass().newInstance();
        F form2 = getFormClass().newInstance();
        assertThat(form, equalTo(form2));
    }

    protected void validate(Fixture<F> fixture) throws Exception {
        F form = getFormClass().newInstance();
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

    protected abstract Class<F> getFormClass();

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
