package com.ksoichiro.task.service;

import com.ksoichiro.task.exception.TestRuntimeException;
import com.ksoichiro.task.exception.TestSQLException;
import org.springframework.stereotype.Service;

@Service
public class ProblematicService {
    public void problematicMethod(boolean trigger) {
        if (trigger) {
            throw new TestRuntimeException("Triggered!");
        }
    }

    public void problematicSQLMethod(boolean trigger) {
        if (trigger) {
            throw new TestSQLException("Triggered!");
        }
    }
}
