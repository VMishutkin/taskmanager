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

import javax.swing.text.LabelView;
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
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTextField;

    private Task task;


    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        task = new Task();
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);

        List<String> userList = userRepository.getAllUserNames();
        TextFields.bindAutoCompletion(usernameTextField, userList);
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
        task.setUsername(usernameTextField.getText());
        task.setDescription(descriptionTextArea.getText());
        task.setTitle(tasknameTextField.getText());
        task.setChangeTime(LocalDateTime.now());

        taskService.updateItem(task);
        stage.close();
        clearFields();

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

        if (auth.getRole() == Role.ADMIN) {
            showUserField(true);
        }
        tasknameTextField.setText(selectedTask.getTitle());
        descriptionTextArea.setText(selectedTask.getDescription());
        usernameTextField.setText(selectedTask.getUsername());
        task = selectedTask;
        stage.showAndWait();

        if (auth.getRole() == Role.ADMIN) {
            showUserField(false);
        }

    }


    private void clearFields() {
        tasknameTextField.setText("");
        descriptionTextArea.setText("");
        usernameTextField.setText("");

    }

    /**
     * Для пользователя админа добавляет строку с выбором пользователя
     *
     * @param isShow если true добавляет поле, если false удаляет
     */
    private void showUserField(boolean isShow) {
        if (isShow) {
            usernameLabel.setVisible(true);
            usernameTextField.setVisible(true);
        } else {
            usernameLabel.setVisible(false);
            usernameTextField.setVisible(false);
        }
    }

}
