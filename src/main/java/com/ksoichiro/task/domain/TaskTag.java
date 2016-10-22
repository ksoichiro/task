package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TaskTag extends BaseEntity {
    private Integer taskId;
    private Integer tagId;

    public TaskTag() {
    }
}
