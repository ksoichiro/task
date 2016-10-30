package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("SELECT t FROM #{#entityName} t "
        + "WHERE t.account = ? ")
    Page<Tag> findByAccount(Account account, Pageable pageable);
}
