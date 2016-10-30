package com.ksoichiro.task.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TagUpdateForm {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;

    public TagUpdateForm() {
    }
}
