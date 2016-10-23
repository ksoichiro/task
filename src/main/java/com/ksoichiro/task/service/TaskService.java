package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.repository.TaskRepository;
import com.ksoichiro.task.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> findByAccount(Account account, Pageable pageable) {
        return taskRepository.findByAccount(account, pageable);
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
        toUpdate.setUpdatedAt(new Date());
        return taskRepository.save(toUpdate);
    }
}
