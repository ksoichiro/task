package com.ksoichiro.task.exception;

import org.springframework.core.NestedRuntimeException;

import java.sql.SQLException;

public class TestSQLException extends NestedRuntimeException {
    public TestSQLException(String msg) {
        super(msg,
            new TestRuntimeException("I know the cause",
                new SQLException("Test error", "9999", 100)));
    }
}
