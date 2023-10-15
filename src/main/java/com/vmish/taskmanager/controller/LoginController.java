package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@FxmlView("loginwindow.fxml")
public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    public Auth getAuth() {
        Auth auth = new Auth();
        auth.setLogin(usernameTextField.getText());
        auth.setPass(passwordField.getText());
        return auth;
    }
}
