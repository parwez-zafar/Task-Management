package com.parwez.service;

import com.parwez.modal.Task;
import com.parwez.modal.TaskStatus;
import com.parwez.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService{

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if(!requesterRole.equals("ROLE_ADMIN")){
            throw new Exception("Only Admin Can Create Task");
        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaksById(Long id) throws Exception {
        return taskRepository.findById(id)
                .orElseThrow(()-> new Exception("Task Not Found with id " + id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTasks = taskRepository.findAll();
        List<Task> filteredTasks = allTasks.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaksById(id);

        if(updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getImage() != null){
            existingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getDescription() != null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus() != null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadline() != null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaksById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taksId) throws Exception {
        Task task = getTaksById(taksId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTasks = taskRepository.findByAssignedUserId(userId);
        List<Task> filteredTasks = allTasks.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taksId) throws Exception {
       Task task = getTaksById(taksId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
