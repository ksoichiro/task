package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"role", "teamAccounts"})
public class Account extends User {
    @Id
    @GeneratedValue
    private Integer id;
    @Getter(onMethod = @__({@Override}))
    private String username;
    private String name;
    @Getter(onMethod = @__({@Override}))
    private String password;
    @Getter(onMethod = @__({@Override}))
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToMany(mappedBy = "account")
    private List<TeamAccount> teamAccounts;

    public Account() {
        super("INVALID", "INVALID", false, false, false, false, new ArrayList<>());
    }

    public Account(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @PrePersist
    @SuppressWarnings("unused")
    public void prePersist() {
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (updatedAt == null) {
            updatedAt = new Date();
        }
    }
}
