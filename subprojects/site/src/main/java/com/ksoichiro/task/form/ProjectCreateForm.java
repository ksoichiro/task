package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.dto.ProjectDTO;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class ProjectCreateForm implements CreateForm<Project, ProjectDTO> {
    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;

    @NotNull
    private Integer teamId;

    @Override
    public ProjectDTO toDTO(Account account) {
        final ProjectDTO projectDTO = new ProjectDTO(account);
        BeanUtils.copyProperties(this, projectDTO);
        return projectDTO;
    }
}
