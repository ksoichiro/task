package com.ksoichiro.task.domain.converter;

import com.ksoichiro.task.App;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TaskRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Sql({"/truncate.sql", "/domain/data-task-status-converter.sql"})
@SpringApplicationConfiguration(App.class)
public class TaskStatusConverterTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void convertFromDatabase() {
        assertThat(taskRepository.findOne(1).getStatus(), is(TaskStatusEnum.NOT_YET));
        assertThat(taskRepository.findOne(2).getStatus(), is(TaskStatusEnum.DOING));
        assertThat(taskRepository.findOne(3).getStatus(), is(TaskStatusEnum.CANCELLED));
        assertThat(taskRepository.findOne(4).getStatus(), is(TaskStatusEnum.HOLD));
        assertThat(taskRepository.findOne(5).getStatus(), is(TaskStatusEnum.DONE));
    }

    @Test
    public void convertToDatabase() {
        Account account = accountRepository.findOne(1);

        for (TaskStatusEnum e : TaskStatusEnum.values()) {
            Task task = new Task("a", e, null, null, account, null);
            task = taskRepository.save(task);
            Integer status = Integer.parseInt(jdbcTemplate.queryForList("SELECT * FROM task WHERE id = ?", new Object[]{task.getId()}).get(0).get("status").toString());
            assertThat(status, is(e.getCode()));
        }
    }
}
