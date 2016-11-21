package com.ksoichiro.task.aspect;

import com.ksoichiro.task.App;
import com.ksoichiro.task.exception.TestRuntimeException;
import com.ksoichiro.task.exception.TestSQLException;
import com.ksoichiro.task.service.ProblematicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class ServiceAdviceTests {
    @Autowired
    private ProblematicService problematicService;

    @Test(expected = TestRuntimeException.class)
    public void handleNestedRuntimeException() {
        problematicService.problematicMethod(true);
    }

    @Test(expected = TestSQLException.class)
    public void handleNestedRuntimeExceptionThatContainsSQLException() {
        problematicService.problematicSQLMethod(true);
    }
}
