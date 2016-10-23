package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TaskCreateForm {
    @NotEmpty
    private String name;

    @NotNull
    private TaskStatusEnum status;

    public TaskCreateForm() {
    }
}
