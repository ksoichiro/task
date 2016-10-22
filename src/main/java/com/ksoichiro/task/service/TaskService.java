package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Task task) {
        task.setUpdatedAt(new Date());
        return taskRepository.save(task);
    }
}
