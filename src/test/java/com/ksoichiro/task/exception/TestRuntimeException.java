package com.ksoichiro.task.exception;

import org.springframework.core.NestedRuntimeException;

public class TestRuntimeException extends NestedRuntimeException {
    public TestRuntimeException(String msg) {
        super(msg);
        this.initCause(new RuntimeException("The cause"));
    }
    public TestRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
