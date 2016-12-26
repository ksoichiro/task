package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import com.ksoichiro.task.util.FormUtils;
import com.ksoichiro.task.validation.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateForm {
    @NotEmpty
    private String name;

    @NotNull
    private TaskStatusEnum status;

    @DateTime
    private String scheduledAt;

    private List<Tag> tags;

    public TaskDTO toTaskDTO(Account account) {
        TaskDTO taskDTO = new TaskDTO(account);
        BeanUtils.copyProperties(this, taskDTO);
        FormUtils.copyDate(this::getScheduledAt, taskDTO::setScheduledAt);
        return taskDTO;
    }
}
