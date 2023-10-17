package com.vmish.taskmanager.model;

//import javax.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.IdGeneratorType;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskid;
    private long userid;
    private String username;
    private String taskName;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime changeTime;
    private Status status;

    public Task() {
    }

    public Task(String username, String taskName, String description) {
        this.username = username;
        this.taskName = taskName;
        this.description = description;
        this.creationTime = LocalDateTime.now();
        this.changeTime = LocalDateTime.now();

    }

    public long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
        return taskName;
    }

    public String getTaskid() {
        return Long.toString(taskid);
    }
}
