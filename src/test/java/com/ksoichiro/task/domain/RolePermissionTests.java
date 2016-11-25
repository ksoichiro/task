package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RolePermissionTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class RolePermission2 extends RolePermission {
    }


    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        RolePermission entity1 = new RolePermission(null, null);
        RolePermission entity2 = new RolePermission();

        assertThat(entity1.equals(entity2), is(true));

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Role role = new Role();
        entity2.setRole(role);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setRole(role);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Permission permission = new Permission();
        entity2.setPermission(permission);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setPermission(permission);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Date now = new Date();
        entity2.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        entity2.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        RolePermission2 entity3 = new RolePermission2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
