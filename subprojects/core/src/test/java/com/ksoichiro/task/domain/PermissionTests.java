package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PermissionTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Permission2 extends Permission {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        Permission entity1 = new Permission(null, null, null);
        Permission entity2 = new Permission();

        assertThat(entity1.equals(entity2), is(true));

        entity2.setCd("p1");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setCd("p1");
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        entity2.setName("Permission 1");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setName("Permission 1");
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        List<RolePermission> rolePermissionList = new ArrayList<>();
        entity2.setRolePermissions(rolePermissionList);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setRolePermissions(rolePermissionList);
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

        Permission2 entity3 = new Permission2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
