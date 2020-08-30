package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "task_id",unique = true)
    private String taskId;
    @Column(name = "task_description")
    private String taskDescription;
    @Column(name = "task_create_date")
    private String taskCreateDate;
    @Column(name = "task_update_date")
    private String taskUpdateDate;
    @Column(name = "task_sequence")
    private String taskSequence;
    @Column(name = "products_id")
    private String productsId;
    @Column(name = "user_role")
    private String userRole;

    public Task(){

    }

    public Task(String taskId, String taskDescription, String taskCreateDate, String taskUpdateDate, String taskSequence, String productsId, String userRole) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.taskCreateDate = taskCreateDate;
        this.taskUpdateDate = taskUpdateDate;
        this.taskSequence = taskSequence;
        this.productsId = productsId;
        this.userRole = userRole;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(String taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getTaskUpdateDate() {
        return taskUpdateDate;
    }

    public void setTaskUpdateDate(String taskUpdateDate) {
        this.taskUpdateDate = taskUpdateDate;
    }

    public String getTaskSequence() {
        return taskSequence;
    }

    public void setTaskSequence(String taskSequence) {
        this.taskSequence = taskSequence;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
