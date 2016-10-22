package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
