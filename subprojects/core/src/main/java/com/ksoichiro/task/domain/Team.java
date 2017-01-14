package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString(exclude = "teamAccounts")
public class Team extends BaseEntity {
    private String cd;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<TeamAccount> teamAccounts;

    public Team() {
    }

    public static Team empty() {
        Team team = new Team("", "", null);
        team.setId(0);
        team.setCreatedAt(emptyDate());
        team.setUpdatedAt(emptyDate());
        return team;
    }
}
