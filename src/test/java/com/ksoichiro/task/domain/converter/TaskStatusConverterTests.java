package com.ksoichiro.task.domain.converter;

import com.ksoichiro.task.App;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.repository.TaskRepository;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Sql({"/truncate.sql", "/domain/data-task-status-converter.sql"})
@SpringApplicationConfiguration(App.class)
public class TaskStatusConverterTests {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void convert() {
        assertThat(taskRepository.findOne(1).getStatus(), is(TaskStatusEnum.NOT_YET));
        assertThat(taskRepository.findOne(2).getStatus(), is(TaskStatusEnum.DOING));
        assertThat(taskRepository.findOne(3).getStatus(), is(TaskStatusEnum.CANCELLED));
        assertThat(taskRepository.findOne(4).getStatus(), is(TaskStatusEnum.HOLD));
        assertThat(taskRepository.findOne(5).getStatus(), is(TaskStatusEnum.DONE));
    }
}
