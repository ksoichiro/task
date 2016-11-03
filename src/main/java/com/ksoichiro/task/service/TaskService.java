package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.QTask;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import com.ksoichiro.task.repository.TaskRepository;
import com.ksoichiro.task.util.DateUtils;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    public Long countByAccount(Account account) {
        return taskRepository.countByAccount(account);
    }

    public Page<Task> findByAccount(Account account, Pageable pageable) {
        return taskRepository.findByAccount(account, pageable);
    }

    public Page<Task> findByAccountAndConditions(Account account, TaskDTO dto, Pageable pageable) {
        QTask task = QTask.task;

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(task.account.id.eq(account.getId()));

        if (!StringUtils.isEmpty(dto.getName())) {
            predicate.and(task.name.contains(dto.getName()));
        }
        if (dto.getStatus() != null) {
            predicate.and(task.status.eq(dto.getStatus()));
        }
        if (dto.getScheduledAt() != null) {
            predicate.and(task.scheduledAt.eq(DateUtils.truncateTime(dto.getScheduledAt())));
        }

        PathBuilder<Task> builder = new PathBuilder<>(Task.class, QTask.task.getMetadata());
        Querydsl querydsl = new Querydsl(entityManager, builder);

        JPQLQuery countQuery = createQuery(predicate);
        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate));

        Path<Task> path = QTask.task;
        Long total = countQuery.count();

        List<Task> content = total > pageable.getOffset() ? query.list(path) : Collections.<Task>emptyList();

        return new PageImpl<>(content, pageable, total);
    }

    public Long countByAccountAndScheduledAtIsToday(Account account) {
        return taskRepository.countByAccountAndScheduledAt(account, DateUtils.truncateTime(new Date()));
    }

    public Page<Task> findByAccountAndScheduledAtIsToday(Account account, Pageable pageable) {
        return taskRepository.findByAccountAndScheduledAt(account, DateUtils.truncateTime(new Date()), pageable);
    }

    public Task findByIdAndAccount(Integer id, Account account) {
        return taskRepository.findByIdAndAccount(id, account);
    }

    @Transactional
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Task task) {
        Task toUpdate = taskRepository.findOne(task.getId());
        if (!task.getAccount().getId().equals(toUpdate.getAccount().getId())) {
            throw new IllegalStateException("Task cannot be updated by this account: owner: " + task.getAccount().getId() + ", updated by: " + toUpdate.getAccount().getId());
        }
        if (task.getName() != null) {
            toUpdate.setName(task.getName());
        }
        if (task.getStatus() != null) {
            toUpdate.setStatus(task.getStatus());
        }
        if (task.getTags() != null) {
            toUpdate.setTags(task.getTags());
        }
        toUpdate.setUpdatedAt(new Date());
        return taskRepository.save(toUpdate);
    }

    private JPAQuery createQuery(Predicate predicate) {
        return new JPAQuery(entityManager)
            .from(QTask.task)
            .leftJoin(QTask.task.tags)
            .where(predicate);
    }
}
