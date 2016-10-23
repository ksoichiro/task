package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Task extends BaseEntity {
    private String name;

    private Date scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Task() {
    }
}
