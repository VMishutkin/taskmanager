package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("loginwindow.fxml")
public class LoginController {
    private Stage stage;
    @FXML
    private VBox dialog;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Auth login(){
        stage.close();
        return new Auth(usernameTextField.getText(), passwordField.getText());
    }
    public void cancel(){
        stage.close();
    }
    public Auth getAuth() {
        stage.showAndWait();
        return login();
    }
}
