package com.ksoichiro.task.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    protected Integer id;
    protected Date createdAt;
    protected Date updatedAt;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (updatedAt == null) {
            updatedAt = new Date();
        }
    }

    static Date emptyDate() {
        return new Date(0);
    }
}
