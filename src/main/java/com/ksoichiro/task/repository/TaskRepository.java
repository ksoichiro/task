package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.account = ?1")
    Page<Task> findByAccount(Account account, Pageable pageable);
}
