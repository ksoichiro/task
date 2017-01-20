package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchForm implements Form<Task> {
    private String name;

    private TaskStatusEnum status;

    public TaskDTO toDTO(Account account) {
        final TaskDTO dto = new TaskDTO(account);
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
