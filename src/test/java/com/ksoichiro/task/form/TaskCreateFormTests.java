package com.ksoichiro.task.form;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Theories.class)
public class TaskCreateFormTests extends AbstractFormTests<TaskCreateForm> {
    @DataPoints
    public static final List<Fixture<TaskCreateForm>> FIXTURES = new ArrayList<Fixture<TaskCreateForm>>(){
        {
            add(new Fixture<>(new TaskCreateForm(null, null, null), true, new HashMap<String, String>() {
                {
                    put("name", "may not be empty");
                    put("status", "may not be null");
                }
            }));
        }
    };

    @Theory
    public void validate(Fixture<TaskCreateForm> fixture) throws Exception {
        super.validate(fixture);
    }

    @Override
    protected Class<TaskCreateForm> getFormClass() {
        return TaskCreateForm.class;
    }
}
