package com.parwez.task_submission_service.service;

import com.parwez.task_submission_service.feignService.TaskService;
import com.parwez.task_submission_service.feignService.UserService;
import com.parwez.task_submission_service.modal.Submission;
import com.parwez.task_submission_service.modal.TaskDto;
import com.parwez.task_submission_service.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService{

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;



    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {

        TaskDto task = taskService.getTaskById(taskId, jwt);

        if(task != null)
        {
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }

        throw new Exception("Task Not Found With Id : " + taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(()-> new Exception("Task Submission Not Found With Id " +  submissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaksSubmissionByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission accepetDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")){
            taskService.completeTaks(submission.getTaskId());
        }
        return submissionRepository.save(submission);
    }
}
