package com.ksoichiro.task.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class TaskCreateForm {
    @NotEmpty
    private String name;

    public TaskCreateForm() {
    }
}
