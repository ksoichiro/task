package com.ksoichiro.task.exception;

public class DuplicateTagNameException extends RuntimeException {
    public DuplicateTagNameException(String msg) {
        super(msg);
    }
}
