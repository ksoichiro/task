package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
