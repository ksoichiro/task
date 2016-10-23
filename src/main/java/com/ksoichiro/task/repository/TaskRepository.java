package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.account = ?1")
    Page<Task> findByAccount(Account account, Pageable pageable);

    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.account = ?1 "
        + "AND t.scheduledAt = ?2")
    Page<Task> findByAccountAndScheduledAt(Account account, Date scheduledAt, Pageable pageable);

    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.id = ?1 "
        + "AND t.account = ?2")
    Task findByIdAndAccount(Integer id, Account account);
}
