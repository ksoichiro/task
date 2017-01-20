package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateForm implements UpdateForm<Task, TaskDTO> {
    @NotNull
    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private TaskStatusEnum status;

    private List<Tag> tags;

    @Override
    public TaskDTO toDTO(Account account) {
        final TaskDTO taskDTO = new TaskDTO(account);
        BeanUtils.copyProperties(this, taskDTO);
        return taskDTO;
    }
}
