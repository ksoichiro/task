package com.ksoichiro.task.form;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Theories.class)
public class TagUpdateFormTests extends AbstractFormTests<TagUpdateForm> {
    @DataPoints
    public static final List<Fixture<TagUpdateForm>> FIXTURES = new ArrayList<Fixture<TagUpdateForm>>(){
        {
            add(new Fixture<>(new TagUpdateForm(null, null), true, new HashMap<String, String>() {
                {
                    put("id", "may not be null");
                    put("name", "may not be empty");
                }
            }));
        }
    };

    @Theory
    @Override
    public void validate(Fixture<TagUpdateForm> fixture) throws Exception {
        super.validate(fixture);
    }

    @Override
    protected Class<TagUpdateForm> getFormClass() {
        return TagUpdateForm.class;
    }
}
