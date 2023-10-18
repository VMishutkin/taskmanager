package com.vmish.taskmanager.service;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Role;
import com.vmish.taskmanager.repository.TaskRepository;
import com.vmish.taskmanager.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private ObservableList<Task> taskList;

    private DateTimeFormatter formatter;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        taskList = FXCollections.observableArrayList();

        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }

    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public Task addTask(Task newTask) {
        Task result = taskRepository.save(newTask);
        taskList.add(result);
        return result;
    }

    public void updateTaskList(Auth auth) {
        taskList.clear();
        if (auth.getRole().equals(Role.ADMIN)) {
            taskList.addAll(taskRepository.findAll());
        } else {
            taskList.addAll(taskRepository.findByUsername(auth.getLogin()));
        }

    }

    public void update(Task selectedItem) {
        taskRepository.save(selectedItem);
    }


}
