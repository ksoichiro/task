package com.ksoichiro.task.form;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Theories.class)
public class TaskUpdateFormTests extends AbstractFormTests<TaskUpdateForm> {
    @DataPoints
    public static final List<Fixture<TaskUpdateForm>> FIXTURES = new ArrayList<Fixture<TaskUpdateForm>>(){
        {
            add(new Fixture<>(new TaskUpdateForm(null, null, null, null), true, new HashMap<String, String>() {
                {
                    put("id", "may not be null");
                    put("name", "may not be empty");
                    put("status", "may not be null");
                }
            }));
        }
    };

    @Theory
    public void validate(Fixture<TaskUpdateForm> fixture) throws Exception {
        super.validate(fixture);
    }

    @Override
    protected Class<TaskUpdateForm> getFormClass() {
        return TaskUpdateForm.class;
    }
}
