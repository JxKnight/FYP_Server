package com.example.fyp.Controller;

import com.example.fyp.Model.*;
import com.example.fyp.Repository.BuyerRepository;
import com.example.fyp.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    TaskRepository TaskRepo;

    @GetMapping("task")
    public List<Task> getAllTasks() {
        return TaskRepo.findAll();
    }

    @PostMapping("createTask")
    public void createTask(@RequestBody Map<String, String> payload) {
        String[] task = payload.get("taskDescription").split("/");
        String[] sequence = payload.get("taskSequence").split("/");
        String[] Role = payload.get("userRole").split("/");
        Integer count = 0;
        List<Task> list = getAllTasks();
        for (Task countList : list) {
            count++;
        }
        Integer x = task.length;
        for (int i=0;i<x;i++) {
            String taskID = "";
            taskID = "Task" + count;
            TaskRepo.save(new Task(taskID, task[i], payload.get("taskCreateDate"), payload.get("taskUpdateDate"), sequence[i], payload.get("productsId"),String.valueOf(0)));
            count++;
        }
    }

    @PostMapping("updateRole")
    public Task updateUser(@RequestBody Map<String, String> payload) {
        return TaskRepo.findByTaskId(payload.get("taskId")).map(task -> {
            task.setUserRole(payload.get("userRole"));
            TaskRepo.save(task);
            return task;
        }).orElseThrow(() -> new NullPointerException("Unable to update empty record"));
    }
}
