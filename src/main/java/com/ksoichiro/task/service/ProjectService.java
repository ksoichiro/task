package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.dto.ProjectDTO;
import com.ksoichiro.task.repository.ProjectRepository;
import com.ksoichiro.task.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Page<Project> findByAccount(Account account, Pageable pageable) {
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("account is null");
        }
        return projectRepository.findByAccount(account, pageable);
    }

    public Page<Project> findByTeam(Team team, Pageable pageable) {
        if (team == null || team.getId() == null) {
            throw new IllegalArgumentException("team is null");
        }
        return projectRepository.findByTeam(team, pageable);
    }

    @Transactional
    public Project create(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            throw new IllegalArgumentException("projectDTO cannot be null");
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project, "id", "team");
        project.setTeam(teamRepository.findOne(projectDTO.getTeamId()));
        return projectRepository.save(project);
    }

    @Transactional
    public Project update(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            throw new IllegalArgumentException("projectDTO cannot be null");
        }
        if (projectDTO.getId() == null || projectDTO.getId() < 0) {
            throw new IllegalArgumentException("project.id cannot be null or minus");
        }
        Project project = projectRepository.findOne(projectDTO.getId());
        BeanUtils.copyProperties(projectDTO, project, "id");
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
    }
}
