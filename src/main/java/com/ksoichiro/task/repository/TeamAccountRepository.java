package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.TeamAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamAccountRepository extends JpaRepository<TeamAccount, Integer> {
}
