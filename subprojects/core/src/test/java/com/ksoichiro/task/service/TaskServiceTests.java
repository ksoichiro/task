package com.ksoichiro.task.service;

import com.ksoichiro.task.App;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TaskRepository;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("/schema-h2.sql")
@Sql({"/truncate.sql", "/data-task.sql"})
@SpringBootTest(classes = App.class)
public class TaskServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TaskService taskService;

    @Test
    public void create() {
        Account account = accountRepository.findOne(1);
        TaskDTO taskDTO = new TaskDTO(account);
        taskDTO.setName("Write a report about Spring");
        taskDTO.setStatus(TaskStatusEnum.DOING);
        Task created = taskService.create(taskDTO);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(greaterThan(1)));
        assertThat(created.getStatus(), is(TaskStatusEnum.DOING));
        assertThat(created.getCreatedAt(), is(notNullValue()));
        assertThat(created.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    public void update() {
        Task task = taskRepository.findOne(1);
        TaskDTO taskDTO = new TaskDTO();
        BeanUtils.copyProperties(task, taskDTO);
        taskDTO.setName("Send a mail to my boss");
        taskDTO.setStatus(TaskStatusEnum.DONE);
        Task updated = taskService.update(taskDTO);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getId(), is(1));
        assertThat(updated.getName(), is("Send a mail to my boss"));
        assertThat(updated.getStatus(), is(TaskStatusEnum.DONE));
        assertThat(updated.getCreatedAt(), is(notNullValue()));
        assertThat(updated.getUpdatedAt(), is(greaterThan(updated.getCreatedAt())));
    }

    @Test(expected = IllegalStateException.class)
    public void updateWithDifferentAccount() {
        Account account = accountRepository.findOne(2);
        TaskDTO taskDTO = new TaskDTO(account);
        taskDTO.setId(1);
        taskDTO.setName("Test");
        taskService.update(taskDTO);
    }
}
