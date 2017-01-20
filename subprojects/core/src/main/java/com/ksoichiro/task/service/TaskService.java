package com.ksoichiro.task.service;

import com.ksoichiro.task.constant.Caches;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class TaskService {
    private final EntityManager entityManager;

    private final TaskRepository taskRepository;

    @Cacheable(cacheNames = Caches.TASK_COUNT, key = "#account.id")
    public Long countByAccount(Account account) {
        return taskRepository.countByAccount(account);
    }

    public Page<Task> findByAccount(Account account, Pageable pageable) {
        return taskRepository.findByAccount(account, pageable);
    }

    public Page<Task> findByAccountAndConditions(TaskDTO dto, Pageable pageable) {
        final QTask task = QTask.task;

        final BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(task.account.id.eq(dto.getAccount().getId()));

        if (!StringUtils.isEmpty(dto.getName())) {
            predicate.and(task.name.contains(dto.getName()));
        }
        if (dto.getStatus() != null) {
            predicate.and(task.status.eq(dto.getStatus()));
        }
        if (dto.getScheduledAt() != null) {
            predicate.and(task.scheduledAt.eq(DateUtils.truncateTime(dto.getScheduledAt())));
        }

        final PathBuilder<Task> builder = new PathBuilder<>(Task.class, QTask.task.getMetadata());
        final Querydsl querydsl = new Querydsl(entityManager, builder);

        final JPQLQuery countQuery = createQuery(predicate);
        final JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate));

        final Path<Task> path = QTask.task;
        final Long total = countQuery.count();

        final List<Task> content = total > pageable.getOffset() ? query.list(path) : Collections.emptyList();

        return new PageImpl<>(content, pageable, total);
    }

    @Cacheable(cacheNames = Caches.TASK_COUNT, key = "#account.id + '-today'")
    public Long countByAccountAndScheduledAtIsToday(Account account) {
        return taskRepository.countByAccountAndScheduledAt(account, DateUtils.today());
    }

    public Page<Task> findByAccountAndScheduledAtIsToday(Account account, Pageable pageable) {
        return taskRepository.findByAccountAndScheduledAt(account, DateUtils.today(), pageable);
    }

    public Task findByIdAndAccount(Integer id, Account account) {
        return taskRepository.findByIdAndAccount(id, account);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = Caches.TASK_COUNT, key = "#taskDTO.account.id"),
        @CacheEvict(cacheNames = Caches.TASK_COUNT, key = "#taskDTO.account.id + '-today'")})
    public Task create(TaskDTO taskDTO) {
        final Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        return taskRepository.save(task);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = Caches.TASK_COUNT, key = "#taskDTO.account.id"),
        @CacheEvict(cacheNames = Caches.TASK_COUNT, key = "#taskDTO.account.id + '-today'")})
    public Task update(TaskDTO taskDTO) {
        final Task toUpdate = taskRepository.findOne(taskDTO.getId());
        if (!taskDTO.getAccount().getId().equals(toUpdate.getAccount().getId())) {
            throw new IllegalStateException("Task cannot be updated by this account: owner: " + taskDTO.getAccount().getId() + ", updated by: " + toUpdate.getAccount().getId());
        }
        if (taskDTO.getName() != null) {
            toUpdate.setName(taskDTO.getName());
        }
        if (taskDTO.getStatus() != null) {
            toUpdate.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getTags() != null) {
            toUpdate.setTags(taskDTO.getTags());
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
