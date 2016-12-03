package com.ksoichiro.task.domain;

import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.converter.TaskStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"parentTask", "tags"})
@AllArgsConstructor
@Slf4j
public class Task extends BaseEntity {
    private String name;

    @Convert(converter = TaskStatusConverter.class)
    private TaskStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task parentTask;

    private Date scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToMany
    @JoinTable(name = "TaskTag",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

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
