package com.vmish.taskmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import com.vmish.taskmanager.service.TaskService;
import com.vmish.taskmanager.model.Task;

@Component
@FxmlView("taskwindow.fxml")
public class TaskController {
    private Stage stage;
    @FXML
    private VBox dialog;

    private TaskService taskService;
    @FXML
    private TextField tasknameTextField;
    @FXML
    private TextArea descriptionTextArea;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Task createNewTask(String username) {
        stage.showAndWait();
        String taskname = tasknameTextField.getText().trim();
        String details = descriptionTextArea.getText().trim();
        return (taskService.addTask(new Task(username, taskname, details)));
    }

    public void save(ActionEvent actionEvent) {
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
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
