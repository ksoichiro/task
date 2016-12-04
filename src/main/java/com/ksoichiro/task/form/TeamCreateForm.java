package com.ksoichiro.task.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class TeamCreateForm {
    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;
}
