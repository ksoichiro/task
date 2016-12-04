package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("select p from #{#entityName} p "
        + "join p.team t "
        + "join t.teamAccounts ta "
        + "where ta.account = ?1 ")
    Page<Project> findByAccount(Account account, Pageable pageable);

    @Query(value = "SELECT p from #{#entityName} p "
        + "JOIN FETCH p.team t "
        + "JOIN FETCH t.teamAccounts ta "
        + "WHERE p.id = ?1 "
        + "AND ta.account = ?2 ")
    Project findByIdAndAccount(Integer id, Account account);

    @Query("select p from #{#entityName} p "
        + "where p.team = ?1 ")
    Page<Project> findByTeam(Team team, Pageable pageable);
}
