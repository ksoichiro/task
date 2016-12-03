package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
