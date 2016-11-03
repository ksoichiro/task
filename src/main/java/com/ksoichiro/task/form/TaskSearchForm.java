package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskSearchForm {
    private String name;

    private TaskStatusEnum status;

    public TaskSearchForm() {
    }
}
