package com.vmish.taskmanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.format.DateTimeFormatter;


public class TaskData {
    private static TaskData instance = new TaskData();
    private ObservableList<Task> taskList;

    private DateTimeFormatter formatter;

    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    public static TaskData getInstance() {
        return instance;
    }
    public void createList(){
        if(taskList == null){
            taskList = FXCollections.observableArrayList();
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public Task addTask(Task newTask) {
        if (!taskList.add(newTask)) {
            return null;
        }
        return newTask;
    }
    public void setTaskList(ObservableList<Task> taskList){
        this.taskList = taskList;
    }


}
