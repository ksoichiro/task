package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
}
