package com.ksoichiro.task.domain;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.converter.TaskStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Slf4j
public class Task extends BaseEntity {
    private String name;

    @Convert(converter = TaskStatusConverter.class)
    private TaskStatusEnum status;

    private Date scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Task() {
    }

    @Override
    public void preSave() {
        super.preSave();
        if (status == null) {
            status = TaskStatusEnum.NOT_YET;
        }
    }
}
