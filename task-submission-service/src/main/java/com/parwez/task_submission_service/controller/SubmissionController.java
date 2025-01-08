package com.parwez.task_submission_service.controller;

import com.parwez.task_submission_service.feignService.TaskService;
import com.parwez.task_submission_service.feignService.UserService;
import com.parwez.task_submission_service.modal.Submission;
import com.parwez.task_submission_service.modal.UserDto;
import com.parwez.task_submission_service.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<Submission> submitTaks(
            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);

        Submission submission = submissionService.submitTask(task_id, github_link, user.getId(), jwt);

        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getTaskSubmissionById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);

        Submission submission = submissionService.getTaskSubmissionById(id);

        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllTaskSubmissions(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);

        List<Submission> submissions = submissionService.getAllTaskSubmissions();

        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getTaksSubmissionByTaskId(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);

        List<Submission> submissions = submissionService.getTaksSubmissionByTaskId(taskId);

        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> accepetDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);

        Submission submission = submissionService.accepetDeclineSubmission(id, status);

        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}
