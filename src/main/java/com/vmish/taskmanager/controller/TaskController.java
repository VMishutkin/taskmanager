package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Role;
import com.vmish.taskmanager.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;
import com.vmish.taskmanager.service.TaskService;
import com.vmish.taskmanager.model.Task;

import java.util.List;

@Component
@FxmlView("taskwindow.fxml")
public class TaskController {
    private Stage stage;
    @FXML
    private VBox dialog;
    @FXML
    private GridPane taskGridPane;
    private TaskService taskService;
    @FXML
    private TextField tasknameTextField;
    @FXML
    private TextArea descriptionTextArea;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
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

    public void save() {
        stage.close();
    }

    public void cancel() {
        stage.close();
    }

    public void editTask(Task selectedItem, Auth auth) {

        TextField usernameTextField = new TextField();
        if (auth.getRole() == Role.ADMIN) {
            showUserField(usernameTextField, true);
        }
        tasknameTextField.setText(selectedItem.getTitle());
        descriptionTextArea.setText(selectedItem.getDescription());
        stage.showAndWait();
        if (auth.getRole() == Role.ADMIN) {
            showUserField(usernameTextField, false);
        }
        taskService.updateItem(selectedItem, usernameTextField.getText(),
                tasknameTextField.getText(), descriptionTextArea.getText());
        clearFields();
    }

    private void clearFields() {
        tasknameTextField.setText("");
        descriptionTextArea.setText("");
    }

    private void showUserField(TextField usernameTextField, boolean isShow) {
        Label usernameLabel = new Label("Пользователь");

        if (isShow) {
            List<String> userList = userRepository.getAllUserNames();
            usernameTextField = new TextField();
            TextFields.bindAutoCompletion(usernameTextField, userList);
            taskGridPane.add(usernameLabel, 0, 1);
            taskGridPane.add(usernameTextField, 1, 1);
        } else {
            taskGridPane.getChildren().remove(usernameLabel);
            taskGridPane.getChildren().remove(usernameTextField);
        }
    }

}
