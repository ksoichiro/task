package com.ksoichiro.task.dto;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {
    private String name;

    private TaskStatusEnum status;

    private Date scheduledAt;

    public TaskDTO() {
    }
}
