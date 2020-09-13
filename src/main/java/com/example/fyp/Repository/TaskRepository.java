package com.example.fyp.Repository;

import com.example.fyp.Model.Role;
import com.example.fyp.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    Optional<Task>findByTaskId(String taskID);
}
