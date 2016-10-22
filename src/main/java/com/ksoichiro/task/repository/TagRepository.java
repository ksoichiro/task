package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
