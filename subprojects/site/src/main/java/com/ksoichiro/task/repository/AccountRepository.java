package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a from #{#entityName} a "
        + "JOIN FETCH a.role "
        + "WHERE a.username = ?1 ")
    Account findByUsername(String username);
}
