package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"tasks"})
@AllArgsConstructor
public class Tag extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private String name;

    @OneToMany
    private List<Task> tasks;

    public Tag() {
    }

    @Override
    public void preSave() {
        super.preSave();
        if (team == null) {
            team = Team.empty();
        }
    }
}
