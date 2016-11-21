package com.ksoichiro.task.form;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Theories.class)
public class TagCreateFormTests extends AbstractFormTests<TagCreateForm> {
    @DataPoints
    public static final List<Fixture<TagCreateForm>> FIXTURES = new ArrayList<Fixture<TagCreateForm>>(){
        {
            add(new Fixture<>(new TagCreateForm(null), true, new HashMap<String, String>() {
                {
                    put("name", "may not be empty");
                }
            }));
        }
    };

    @Theory
    public void validate(Fixture<TagCreateForm> fixture) throws Exception {
        super.validate(fixture);
    }

    @Override
    protected Class<TagCreateForm> getFormClass() {
        return TagCreateForm.class;
    }
}
