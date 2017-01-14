package com.ksoichiro.task.config;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class MiddleVersionPathStrategyTests {
    @Test
    public void addVersion() {
        MiddleVersionPathStrategy strategy = new MiddleVersionPathStrategy("lib/", "abc");
        assertThat(strategy.addVersion("foo.js", "whatever"), is("foo.js"));
        assertThat(strategy.addVersion("lib/foo.js", "whatever"), is("lib/abc/foo.js"));
        assertThat(strategy.addVersion("/lib/foo.js", "whatever"), is("lib/abc/foo.js"));
        assertThat(strategy.addVersion(".config", "whatever"), is(".config"));
    }

    @Test
    public void removeVersion() {
        MiddleVersionPathStrategy strategy = new MiddleVersionPathStrategy("lib/", "abc");
        assertThat(strategy.removeVersion("lib/abc/foo.js", "whatever"), is("lib/foo.js"));
    }

    @Test
    public void extractVersion() {
        MiddleVersionPathStrategy strategy = new MiddleVersionPathStrategy("lib/", "abc");
        assertThat(strategy.extractVersion("lib/abc/foo.js"), is("abc"));
        assertThat(strategy.extractVersion("lib/def/foo.js"), is(nullValue()));
    }
}
