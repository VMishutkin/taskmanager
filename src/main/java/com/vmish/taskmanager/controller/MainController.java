package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.model.TaskData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.model.TaskData;

import java.io.IOException;
import java.util.Optional;
@Component
@FxmlView("mainwindow.fxml")
public class MainController {

    @FXML
    private VBox mainVBox;
    @FXML
    private ListView taskListView;
    @FXML
    private Label userIdLabel;
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


    public void initialize(){
        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {

            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task oldTask, Task newTask) {
            if(newTask != null){
                Task item = (Task) taskListView.getSelectionModel().getSelectedItem();
                userIdLabel.setText(""+item.getUserid());
                usernameLabel.setText(item.getUsername());
                tasknameLabel.setText(item.getTaskName());
                descriptionTextArea.setText(item.getDescription());
                creationDateLabel.setText(TaskData.getInstance().getFormatter()
                        .format(item.getCreationTime()));
                changedDateLabel.setText(TaskData.getInstance().getFormatter()
                        .format(item.getCreationTime()));
                statusLabel.setText("new");

            }
            }
        });
        taskListView.setItems(TaskData.getInstance().getTaskList());
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskListView.getSelectionModel().selectFirst();

    }




    @FXML
    public void showNewTaskDialogue(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vmish/taskmanagerfx/fxml/taskwindow.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
/*            TaskController controller = fxmlLoader.getController();
            controller.createTaskFromDialog();*/

        }

    }


}
