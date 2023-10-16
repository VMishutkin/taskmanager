package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
/*    public Auth getAuth() {
        Auth auth = new Auth();
        auth.setLogin(usernameTextField.getText());
        auth.setPass(passwordField.getText());
        return auth;
    }*/

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
    }

    public void show() {
        stage.show();
    }
}
