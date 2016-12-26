package com.ksoichiro.task.form;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskSearchForm {
    private String name;

    private TaskStatusEnum status;

    public TaskSearchForm() {
    }

    public TaskDTO toTaskDTO() {
        TaskDTO dto = new TaskDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
