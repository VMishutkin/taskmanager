package com.vmish.taskmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.service.TaskService;

@Component
@FxmlView("taskwindow.fxml")
public class TaskController {
    private Stage stage;
    @FXML
    private VBox dialog;

    @FXML
    private TextField tasknameTextField;
    @FXML
    private TextArea descriptionTextArea;
    private TaskService taskService;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
    }

    public void show() {
        stage.show();
    }

    @FXML
    void click(ActionEvent actionEvent) {
        stage.close();
    }


}

/*@Component
@FxmlView("taskwindow.fxml")
public class TaskController {

    @FXML
    private TextField tasknameTextField;
    @FXML
    private TextArea descriptionTextArea;
    private TaskService taskService;

    @Autowired
    public void setTaskData(TaskService taskService) {
        this.taskService = taskService;
    }


    public Task createTaskFromDialog(String login) {
        String taskname = tasknameTextField.getText().trim();
        String details = descriptionTextArea.getText().trim();
        String username = login;
        return (taskService.addTask(new Task(username,taskname, details)));
    }


}*/
