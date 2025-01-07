package com.parwez.service;

import com.parwez.modal.Task;
import com.parwez.modal.TaskStatus;

import java.util.List;

public interface TaskService {


    Task createTask(Task task, String requesterRole) throws Exception;

    Task getTaksById(Long id) throws Exception;

    List<Task> getAllTask(TaskStatus status);

    Task updateTask(Long id, Task updatedTask, Long userId) throws Exception;

    void deleteTask(Long id) throws Exception;

    Task assignedToUser(Long userId, Long taksId) throws Exception;

    List<Task> assignedUsersTask(Long userId, TaskStatus status);

    Task completeTask(Long taksId) throws Exception;


}
