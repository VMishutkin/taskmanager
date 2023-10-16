package com.vmish.taskmanager.service;

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
        taskList.addAll(taskRepository.findAll());
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public ObservableList<Task> getTaskList() {
        return taskList;
    }

/*    public static TaskData getInstance() {
        return instance;
    }*/
/*    public void createList(){
        if(taskList == null){
            taskList = FXCollections.observableArrayList();

        }
    }*/

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public Task addTask(Task newTask) {
        Task result = taskRepository.save(newTask);
        taskList.add(taskRepository.save(newTask));
        return result;
    }
    public void setTaskList(ObservableList<Task> taskList){
        this.taskList = taskList;
    }


/*    public void saveList() {
        taskRepository.saveAll(taskList);
    }*/
}
