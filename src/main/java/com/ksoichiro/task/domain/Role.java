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
public class Role extends BaseEntity {
    private String cd;
    private String name;

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;

    public Role() {
    }
}
