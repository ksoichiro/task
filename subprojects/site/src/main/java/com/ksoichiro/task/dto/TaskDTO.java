package com.ksoichiro.task.dto;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Integer id;

    private String name;

    private TaskStatusEnum status;

    private Date scheduledAt;

    private Account account;

    private List<Tag> tags;

    public TaskDTO(Account account) {
        this.account = account;
    }
}
