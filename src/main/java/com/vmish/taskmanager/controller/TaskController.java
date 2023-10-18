package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Role;
import com.vmish.taskmanager.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import java.awt.event.ActionEvent;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Класс с окном для создания и редактирования задач
 */
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
    private String username;
    @FXML
    private Button saveButton;
    @FXML
    private Button createButton;

    private Task task;
    TextField usernameTextField;


    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        task = new Task();
         usernameTextField = new TextField();
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * отображает окно создания новой задачи под активным пользователем
     *
     * @param username Имя активного пользователя
     */
    public void createNewTask(String username) {
        clearFields();
        this.username = username;
        createButton.setVisible(true);
        saveButton.setVisible(false);
        stage.showAndWait();
    }

    /**
     * При нажатии на кнопку сохранить сохраняется новый пользователь
     */
    public void create() {
        String taskname = tasknameTextField.getText().trim();
        String details = descriptionTextArea.getText().trim();
        taskService.addTask(new Task(username, taskname, details));

        stage.close();
        clearFields();
    }

    public void save() {

        taskService.updateItem(task);
        stage.close();

    }

    /**
     * Метод закрытия окна при нажатии Cancel
     */
    public void cancel() {
        clearFields();
        stage.close();
    }

    /**
     * Метод редактирования задачи
     *
     * @param selectedTask изменяемая задача
     * @param auth         если права админа разрешаем менять пользователя
     */
    public void editTask(Task selectedTask, Auth auth) {
        createButton.setVisible(false);
        saveButton.setVisible(true);
        clearFields();
        usernameTextField = new TextField();
        if (auth.getRole() == Role.ADMIN) {
            showUserField(usernameTextField, true);
        }
        tasknameTextField.setText(selectedTask.getTitle());
        descriptionTextArea.setText(selectedTask.getDescription());
       // username = usernameTextField.getText();
        task = Task.copy(selectedTask);
        username = usernameTextField.getText();

        stage.showAndWait();
        task.setUsername(usernameTextField.getText());
        task.setDescription(descriptionTextArea.getText());
        task.setTitle(tasknameTextField.getText());
        task.setChangeTime(LocalDateTime.now());

        if (auth.getRole() == Role.ADMIN) {
            showUserField(usernameTextField, false);
        }

    }


    private void clearFields() {
        tasknameTextField.setText("");
        descriptionTextArea.setText("");
    }

    /**
     * Для пользователя админа добавляет строку с выбором пользователя
     *
     * @param usernameTextField поле набора имени пользователя с дозаполнением
     * @param isShow            если true добавляет поле, если false удаляет
     */
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
