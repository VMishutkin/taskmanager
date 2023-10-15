package com.vmish.taskmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.service.TaskService;

@Component
@FxmlView("taskwindow.fxml")
public class TaskController {
    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField tasknameTextField;
    @FXML
    private TextArea descriptionTextArea;
    private TaskService taskService;

    @Autowired
    public void setTaskData(TaskService taskService) {
        this.taskService = taskService;
    }


    public Task createTaskFromDialog() {
        String username = usernameTextField.getText().trim();
        String taskname = tasknameTextField.getText().trim();
        String details = descriptionTextArea.getText().trim();
        return (new Task(1, username, taskname, details));


    }


}
