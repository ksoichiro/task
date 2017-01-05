package com.ksoichiro.task.converter;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TaskStatusToStringConverter implements Converter<TaskStatusEnum, String> {
    private MessageSource messageSource;

    @Override
    public String convert(TaskStatusEnum source) {
        return messageSource.getMessage("task.status." + source.name(), null, Locale.getDefault());
    }
}
