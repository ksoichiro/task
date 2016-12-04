package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity {
    private String cd;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Project() {
    }

    @Override
    public void preSave() {
        super.preSave();
        if (team == null) {
            team = Team.empty();
        }
    }
}
