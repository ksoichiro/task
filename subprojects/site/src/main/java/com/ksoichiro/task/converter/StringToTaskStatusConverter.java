package com.ksoichiro.task.converter;

import com.ksoichiro.task.constant.TaskStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTaskStatusConverter implements Converter<String, TaskStatusEnum> {
    @Override
    public TaskStatusEnum convert(String source) {
        return TaskStatusEnum.fromCode(Integer.parseInt(source));
    }
}
