package com.ksoichiro.task.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class TagCreateForm {
    @NotEmpty
    private String name;

    public TagCreateForm() {
    }
}
