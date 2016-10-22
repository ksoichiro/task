package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Team extends BaseEntity {
    private String cd;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<TeamAccount> teamAccounts;

    public Team() {
    }
}
