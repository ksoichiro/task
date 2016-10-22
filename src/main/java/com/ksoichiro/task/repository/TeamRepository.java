package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
