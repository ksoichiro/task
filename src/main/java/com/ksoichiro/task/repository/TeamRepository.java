package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t FROM #{#entityName} t "
        + "JOIN t.teamAccounts ta "
        + "WHERE ta.account = ?1 ")
    Page<Team> findByAccount(Account account, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t "
        + "JOIN t.teamAccounts ta "
        + "WHERE ta.account = ?1 ")
    List<Team> findByAccount(Account account);
}
