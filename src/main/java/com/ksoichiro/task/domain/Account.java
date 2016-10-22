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
public class Account extends BaseEntity {
    private String username;
    private String name;
    private String password;
    private boolean enabled;

    @OneToMany(mappedBy = "account")
    private List<TeamAccount> teamAccounts;

    public Account() {
    }
}
