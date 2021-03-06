package com.ksoichiro.task.repository;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("SELECT t FROM #{#entityName} t "
        + "WHERE t.account = ?1 ")
    Page<Tag> findByAccount(Account account, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t "
        + "WHERE t.account = ?1 ")
    List<Tag> findByAccount(Account account);

    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.id = ?1 "
        + "AND t.account = ?2")
    Tag findByIdAndAccount(Integer id, Account account);

    @Query("SELECT t from #{#entityName} t "
        + "WHERE t.name = ?1 "
        + "AND t.account = ?2")
    Tag findByNameAndAccount(String name, Account account);
}
