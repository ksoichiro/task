package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.dto.ProjectDTO;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateForm implements UpdateForm<Project, ProjectDTO> {
    @NotNull
    @Getter(onMethod = @__({@Override}))
    private Integer  id;

    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;

    @Override
    public ProjectDTO toDTO(Account account) {
        final ProjectDTO projectDTO = new ProjectDTO(account);
        BeanUtils.copyProperties(this, projectDTO);
        return projectDTO;
    }
}
