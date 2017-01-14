package com.ksoichiro.task.config;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PrefixAndFixedVersionStrategyTests {
    @Test
    public void getResourceVersion() {
        assertThat(new PrefixAndFixedVersionStrategy("whatever", "version").getResourceVersion(null), is("version"));
    }
}
