package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<Task, Integer> {
}
