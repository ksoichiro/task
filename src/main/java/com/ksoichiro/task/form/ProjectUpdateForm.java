package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.dto.ProjectDTO;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateForm {
    @NotNull
    private Integer id;

    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;

    public ProjectDTO toProjectDTO(Account account) {
        ProjectDTO projectDTO = new ProjectDTO(account);
        BeanUtils.copyProperties(this, projectDTO);
        return projectDTO;
    }

    public void copyFrom(Project project) {
        BeanUtils.copyProperties(project, this);
    }
}
