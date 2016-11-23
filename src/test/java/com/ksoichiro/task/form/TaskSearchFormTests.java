package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Theories.class)
public class TaskSearchFormTests extends AbstractFormTests<TaskSearchForm> {
    @DataPoints
    public static final List<Fixture<TaskSearchForm>> FIXTURES = new ArrayList<Fixture<TaskSearchForm>>(){
        {
            add(new Fixture<>(new TaskSearchForm("foo", TaskStatusEnum.NOT_YET), false, new HashMap<>()));
        }
    };

    @Theory
    public void validate(Fixture<TaskSearchForm> fixture) throws Exception {
        super.validate(fixture);
    }

    @Override
    protected Class<TaskSearchForm> getFormClass() {
        return TaskSearchForm.class;
    }
}
