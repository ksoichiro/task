package com.ksoichiro.task.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class TeamUpdateForm {
    @NotNull
    private Integer id;

    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;
}
