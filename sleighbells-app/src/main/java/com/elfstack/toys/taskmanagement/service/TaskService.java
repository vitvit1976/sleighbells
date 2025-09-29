package com.elfstack.toys.taskmanagement.service;

import com.elfstack.toys.taskmanagement.domain.TYPE_ANY_TASK;
import com.elfstack.toys.taskmanagement.domain.Task;
import com.elfstack.toys.taskmanagement.domain.TaskRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@PreAuthorize("isAuthenticated()")
public class TaskService {

    private final TaskRepository taskRepository;
    private final Clock clock;

    TaskService(TaskRepository taskRepository, Clock clock) {
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    @Transactional
    public void updateTask(Task task, String description, @Nullable LocalDate dueDate, TYPE_ANY_TASK typeAnyTask) {
        task.setDescription(description);
        task.setCreationDate(clock.instant());
        task.setDueDate(dueDate);
        task.setTypeAnyTask(typeAnyTask);
        taskRepository.saveAndFlush(task);
    }

    public void updateTask(Task task, TYPE_ANY_TASK typeAnyTask) {
        task.setCreationDate(clock.instant());
        task.setTypeAnyTask(typeAnyTask);
        taskRepository.saveAndFlush(task);
    }

    @Transactional
    public void createTask(String description, @Nullable LocalDate dueDate) {
        if ("fail".equals(description)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var task = new Task();
        updateTask(task, description, dueDate, TYPE_ANY_TASK.INBOX);
    }


    @Transactional
    public void createTask(String description, @Nullable LocalDate dueDate, TYPE_ANY_TASK typeAnyTask) {
        if ("fail".equals(description)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var task = new Task();
        updateTask(task, description, dueDate, typeAnyTask);
    }

    @Transactional(readOnly = true)
    public List<Task> list(Pageable pageable) {
        return taskRepository.findAllBy(pageable).toList();
    }

    @Transactional
    public void delTask(Task task) {
        taskRepository.delete(task);
    }
}
