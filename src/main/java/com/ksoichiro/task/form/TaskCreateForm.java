package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskCreateForm {
    @NotEmpty
    private String name;

    @NotNull
    private TaskStatusEnum status;

    private List<Tag> tags;

    public TaskCreateForm() {
    }
}
