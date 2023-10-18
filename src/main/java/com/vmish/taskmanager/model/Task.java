package com.vmish.taskmanager.model;

//import javax.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * Класс представляющий задачу
 */
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskid;
    private String username;
    private String title;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime changeTime;
    private Status status;

    public Task() {
    }

    public Task(String username, String title, String description) {
        this.username = username;
        this.title = title;
        this.status = Status.NEW;
        this.description = description;
        this.creationTime = LocalDateTime.now();
        this.changeTime = LocalDateTime.now();

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTaskid() {
        return Long.toString(taskid);
    }

    public static Task copy(Task selectedTask) {

        Task copiedTask= new Task();
        copiedTask.taskid = selectedTask.taskid;
        copiedTask.username = selectedTask.username;
        copiedTask.title = selectedTask.title;
        copiedTask.status = selectedTask.status;
        copiedTask.description = selectedTask.description;
        copiedTask.creationTime = selectedTask.creationTime;
        copiedTask.changeTime =  LocalDateTime.now();
       return  copiedTask;
    }
}
