package com.ksoichiro.task.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    protected Integer id;

    @Getter
    @Setter
    protected Date createdAt;

    @Getter
    @Setter
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
