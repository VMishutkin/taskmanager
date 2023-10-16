package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.service.TaskService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("mainwindow.fxml")
public class MainController {

    private final FxControllerAndView<TaskController, VBox> taskDialog;
    private final FxControllerAndView<LoginController, VBox> loginDialog;
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

    public MainController(FxControllerAndView<TaskController, VBox> taskDialog, FxControllerAndView<LoginController, VBox> loginDialog, TaskService taskService) {
        this.loginDialog = loginDialog;
        this.taskDialog = taskDialog;
        this.taskService = taskService;
    }

    public void initialize() {
        auth = new Auth();
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
                    statusLabel.setText("new");

                }
            }
        });
        taskListView.setItems(taskService.getTaskList());

    }
    @FXML
    public void showNewTaskDialogue() {
        //taskdialog.getController().show();
        taskDialog.getController().createNewTask(auth.getLogin());
    }
    @FXML
    public void showLoginDialog() {
        auth = loginDialog.getController().getAuth();
        if(!auth.getLogin().isEmpty()){
            System.out.println("Login success");
        }
    }
}


/*
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/vmish/taskmanager/controller/loginwindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*//*

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LoginController controller = fxmlLoader.getController();
            Auth auth = controller.getAuth();
            System.out.println("login " + auth.getLogin() + ", pass " + auth.getPass());
            //taskService.addTask(newTask);

        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/vmish/taskmanager/controller/loginwindow.fxml"));
        try {
        dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
        throw new RuntimeException(e);
        }*//*

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        LoginController controller = fxmlLoader.getController();
        Auth auth = controller.getAuth();
        System.out.println("login " + auth.getLogin() + ", pass " + auth.getPass());
        //taskService.addTask(newTask);

        }
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
                    statusLabel.setText("new");

                }
            }
        });*/
/*

        taskListView.setItems(taskService.getTaskList());
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskListView.getSelectionModel().selectFirst();*/



  /*  @FXML
    public void showLoginDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/vmish/taskmanager/controller/loginwindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LoginController controller = fxmlLoader.getController();
            auth = controller.getAuth();
            System.out.println("login - " + auth.getLogin() + ", pass - " + auth.getPass());

        }
    }*/


/*    @FXML
    public void showNewTaskDialogue() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/vmish/taskmanager/controller/taskwindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TaskController controller = fxmlLoader.getController();
            Task newTask = controller.createTaskFromDialog(auth.getLogin());
            taskService.addTask(newTask);

        }

    }*/

