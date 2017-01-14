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
@ToString(exclude = "rolePermissions")
@AllArgsConstructor
public class Permission extends BaseEntity {
    private String cd;
    private String name;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

    public Permission() {
    }
}
