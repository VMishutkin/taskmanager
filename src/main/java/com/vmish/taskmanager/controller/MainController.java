package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Status;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.service.TaskService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Контроллер главного окна
 */
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
        setDisableButtons(true);
        setListAndViewBinding();
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setChangingStatusBinding();
    }

    /**
     * Устанавливаем привязку ListView к taskService.taskList. Добавляем отображение всех полей в правой части окна
     */
    private void setListAndViewBinding() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Task>) (observableValue, oldTask, newTask) -> {
            if (newTask != null) {
                Task item = (Task) taskListView.getSelectionModel().getSelectedItem();
                taskIdLabel.setText(item.getTaskid());
                usernameLabel.setText(item.getUsername());
                tasknameLabel.setText(item.getTitle());
                descriptionTextArea.setText(item.getDescription());
                creationDateLabel.setText(taskService.getFormatter()
                        .format(item.getCreationTime()));
                changedDateLabel.setText(taskService.getFormatter()
                        .format(item.getChangeTime()));
                statusLabel.setText(item.getStatus().getStatusName());
            }
        });
        taskListView.setItems(taskService.getTaskList());
    }

    /**
     * Меняет текст кнопки - если задача создана можно взять в работу, задачу в работе можно закрыть,
     * а закрытую задачу вернуть в работу
     */
    private void setChangingStatusBinding() {
        changeStatusButton.textProperty().bind(Bindings.createStringBinding(() -> {
            Task selectedItem = (Task) taskListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return "Изменить статус";
            } else if (statusLabel.textProperty().getValue().equals(Status.ACCEPTED.name())) {
                return "Закрыть задачу";
            } else if (statusLabel.textProperty().getValue().equals(Status.CLOSED.name())) {
                return "Вернуть в работу";
            } else {
                return "Взять в работу";
            }
        }, statusLabel.textProperty()));
    }
    /**
    *Метод для включения-отключения кнопок управления задачами
     */
    private void setDisableButtons(boolean disable) {
        newTaskButton.setDisable(disable);
        editButton.setDisable(disable);
        changeStatusButton.setDisable(disable);
    }

    /**
     * метод устанавливающий текст статуса согласно перечислению Роль
     */
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
        taskService.updateTaskList(auth);
    }

    /**
     * Вызывает метод открывающий окно создания задачи
     */
    @FXML
    public void showNewTaskDialogue() {
        //taskdialog.getController().show();
        taskDialog.getController().createNewTask(auth.getLogin());
    }

    /**
     * Вызывает метод отображения окна создания нового пользователя для тестирования приложения
     */
    public void showCreateUserWindow() {
        loginDialog = fxWeaver.load(LoginController.class);
        loginDialog.getController().createUser();
    }

    /**
     * Вызывает метод отображения окна авторизации пользователя
     * если получен токен с именем и правами активируем кнопки и выгружаем список задач
     */
    public void showAuthWindow() {
        loginDialog = fxWeaver.load(LoginController.class);
        auth = loginDialog.getController().getAuth();
        if (auth !=null && auth.getLogin()!=null) {
            setDisableButtons(false);
            taskService.updateTaskList(auth);
        }
    }

    /**
     * Вызываем метод отображения окна редактирования задачи
     */
    public void editTask() {
        Task selectedItem = (Task) taskListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            taskDialog.getController().editTask(selectedItem, auth);
            taskService.updateTaskList(auth);
            taskListView.getSelectionModel().select(selectedItem);
        }
    }

}


