package com.ksoichiro.task.domain.converter;

import com.ksoichiro.task.constant.TaskStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * By default, Enum is converted with its ordinal(), but if it can be changed accidentally,
 * therefore I use the converters.
 */
@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskStatusEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public TaskStatusEnum convertToEntityAttribute(Integer dbData) {
        return TaskStatusEnum.fromCode(dbData);
    }
}
