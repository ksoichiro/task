package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RolePermission extends BaseEntity {
    private Integer roleId;
    private Integer permissionId;

    public RolePermission() {
    }
}
