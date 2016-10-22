package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
