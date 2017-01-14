package com.ksoichiro.task.converter;

import com.ksoichiro.task.App;
import com.ksoichiro.task.constant.TaskStatusEnum;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringApplicationConfiguration(App.class)
public class TaskStatusToStringConverterJaTests {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    private static Locale defaultLocale = Locale.getDefault();

    @Autowired
    private TaskStatusToStringConverter converter;

    @BeforeClass
    public static void setUpClass() {
        Locale.setDefault(Locale.JAPANESE);
    }

    @AfterClass
    public static void tearDownClass() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void convert() {
        assertThat(converter.convert(TaskStatusEnum.NOT_YET), is("未着手"));
        assertThat(converter.convert(TaskStatusEnum.DOING), is("作業中"));
        assertThat(converter.convert(TaskStatusEnum.CANCELLED), is("中止"));
        assertThat(converter.convert(TaskStatusEnum.HOLD), is("保留"));
        assertThat(converter.convert(TaskStatusEnum.DONE), is("完了"));
    }
}
