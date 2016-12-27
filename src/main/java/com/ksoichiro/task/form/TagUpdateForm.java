package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdateForm {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;

    public void copyFrom(Tag tag) {
        BeanUtils.copyProperties(tag, this);
    }
}
