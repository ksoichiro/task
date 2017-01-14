package com.ksoichiro.task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/git.properties")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitProperties {
    private final Environment environment;

    public String getCommitId() {
        return environment.getProperty("git.commit.id");
    }
}
