package com.parwez.repository;

import com.parwez.modal.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    public List<Task> findByAssignedUserId(Long userId);
}
