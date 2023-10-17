package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Status;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.service.TaskService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@FxmlView("mainwindow.fxml")
public class MainController {

    private final FxWeaver fxWeaver;
    private final FxControllerAndView<TaskController, VBox> taskDialog;
    private FxControllerAndView<LoginController, VBox> loginDialog;
    @FXML
    private ListView taskListView;
    @FXML
    private Label taskIdLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label tasknameLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label creationDateLabel;
    @FXML
    private Label changedDateLabel;
    @FXML
    private Label statusLabel;
    private TaskService taskService;
    private Auth auth;
    @FXML
    private Button newTaskButton;
    @FXML
    private Button editButton;
    @FXML
    private Button changeStatusButton;

    public MainController(FxWeaver fxWeaver, FxControllerAndView<TaskController, VBox> taskDialog,
                          FxControllerAndView<LoginController, VBox> loginDialog, TaskService taskService) {
        this.fxWeaver = fxWeaver;
        this.loginDialog = loginDialog;
        this.taskDialog = taskDialog;
        this.taskService = taskService;
    }

    public void initialize() {
        newTaskButton.setDisable(true);
        editButton.setDisable(true);
        changeStatusButton.setDisable(true);
        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {

            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task oldTask, Task newTask) {
                if (newTask != null) {
                    Task item = (Task) taskListView.getSelectionModel().getSelectedItem();
                    taskIdLabel.setText("" + item.getTaskid());
                    usernameLabel.setText(item.getUsername());
                    tasknameLabel.setText(item.getTaskName());
                    descriptionTextArea.setText(item.getDescription());
                    creationDateLabel.setText(taskService.getFormatter()
                            .format(item.getCreationTime()));
                    changedDateLabel.setText(taskService.getFormatter()
                            .format(item.getCreationTime()));
                    statusLabel.setText(item.getStatus().name());

                }
            }
        });
        taskListView.setItems(taskService.getTaskList());
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



        changeStatusButton.textProperty().bind(Bindings.createStringBinding(() -> {
            Task selectedItem = (Task) taskListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return "Изменить статус";
            } else if (statusLabel.textProperty().getValue().equals(Status.ACCEPTED.name())) {
                return "Закрыть задачу";
            } else if (statusLabel.textProperty().getValue().equals(Status.CLOSED.name())) {
                return "Вернуть в работу";
            } else {
                return "Взять в работу?";
            }
        }, statusLabel.textProperty()));


    }

    public void changeStatus() {
        Task selectedItem = (Task) taskListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        } else if (selectedItem.getStatus() == Status.NEW) {
            selectedItem.setStatus(Status.ACCEPTED);
        } else if (selectedItem.getStatus() == Status.ACCEPTED) {
            selectedItem.setStatus(Status.CLOSED);
        } else {
            selectedItem.setStatus(Status.ACCEPTED);
        }
        selectedItem.setChangeTime(LocalDateTime.now());
        statusLabel.setText(selectedItem.getStatus().name());
        changedDateLabel.setText(taskService.getFormatter()
                .format(selectedItem.getCreationTime()));
        taskService.update(selectedItem);
        taskService.setTaskList(auth);
    }

    @FXML
    public void showNewTaskDialogue() {
        //taskdialog.getController().show();
        taskDialog.getController().createNewTask(auth.getLogin());
    }

    public void showCreateUserWindow() {
        loginDialog = fxWeaver.load(LoginController.class);
        loginDialog.getController().createUser();
    }


    public void showAuthWindow() {
        loginDialog = fxWeaver.load(LoginController.class);
        auth = loginDialog.getController().getAuth();
        if (!auth.getLogin().isEmpty()) {
            newTaskButton.setDisable(false);
            editButton.setDisable(false);
            changeStatusButton.setDisable(false);
            taskService.setTaskList(auth);
        }
    }

    public void editTask(){
        Task selectedItem = (Task) taskListView.getSelectionModel().getSelectedItem();
        taskDialog.getController().editTask(selectedItem, auth);
        taskService.setTaskList(auth);
    }

}


