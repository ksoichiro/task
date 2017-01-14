package com.ksoichiro.task.domain;

import org.junit.Test;

import javax.persistence.Entity;

public class EntityTests extends LombokTests {
    @Test
    public void test() throws Exception {
        super.test(Entity.class, "com.ksoichiro.task.domain");
    }
}
