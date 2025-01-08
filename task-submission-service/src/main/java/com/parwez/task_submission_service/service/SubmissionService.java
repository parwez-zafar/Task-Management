package com.parwez.task_submission_service.service;

import com.parwez.task_submission_service.modal.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubmissionService {

    Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllTaskSubmissions();

    List<Submission> getTaksSubmissionByTaskId(Long taskId);

    Submission accepetDeclineSubmission(Long id, String status) throws  Exception;


}

