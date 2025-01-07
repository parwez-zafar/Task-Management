package com.parwez.controller;

import com.parwez.feignService.UserService;
import com.parwez.modal.Task;
import com.parwez.modal.TaskStatus;
import com.parwez.modal.UserDto;
import com.parwez.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Task> createTask(
            @RequestBody Task task,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        Task createdTask = taskService.createTask(task, user.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        Task task = taskService.getTaksById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignedUsersTask(user.getId(), status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/getall")
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.getAllTask(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        Task task = taskService.assignedToUser(userId, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto user = userService.getUserProfile(jwt);
        Task task = taskService.updateTask(id, req, user.getId());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTaks(
            @PathVariable Long id
            ) throws Exception {

        Task task = taskService.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) throws Exception {

        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
