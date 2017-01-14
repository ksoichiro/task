package com.ksoichiro.task.constant;

public enum TaskStatusEnum {
    NOT_YET(0),
    DOING(1),
    CANCELLED(3),
    HOLD(2),
    DONE(4);

    private int code;

    TaskStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TaskStatusEnum fromCode(int code) {
        for (TaskStatusEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
