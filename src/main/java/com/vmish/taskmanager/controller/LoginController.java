package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.MyUser;
import com.vmish.taskmanager.model.Role;
import com.vmish.taskmanager.model.Task;
import com.vmish.taskmanager.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private Button loginButton;
    @FXML
    private GridPane loginGridPane;
    private boolean isNowCreated;


    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @FXML
    public void initialize() {
        isNowCreated = false;
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Auth login() {

        Optional<MyUser> checkedUser = userRepository.findByUsername(usernameTextField.getText());

        if (checkedUser.isEmpty()) {

        } else if (isNowCreated) {
            stage.close();
            isNowCreated = false;

        } else {
            MyUser user = checkedUser.get();
            stage.close();
            return new Auth(user.getUsername(), user.getRole());
        }

        return null;
    }

    public void cancel() {
        stage.close();
    }

    public Auth getAuth() {
        stage.showAndWait();
        return login();
    }

    public void createUser() {
        loginButton.setText("Save");
        ComboBox rolesComboBox = new ComboBox();
        List<String> roles = Stream.of(Role.values()).map(Enum::name).collect(Collectors.toList());
        rolesComboBox.getItems().addAll(roles);
        Label userRole = new Label("Роль");

        loginGridPane.add(rolesComboBox, 1, 4);
        loginGridPane.add(userRole, 0, 4);
        isNowCreated = true;
        loginButton.setOnAction(v -> stage.close());
        stage.showAndWait();

        MyUser newUser = new MyUser(usernameTextField.getText(), passwordField.getText(),
                Role.valueOf(rolesComboBox.getValue().toString()));
        userRepository.save(newUser);

        loginButton.setText("Login");
        loginGridPane.getChildren().remove(rolesComboBox);
        loginGridPane.getChildren().remove(userRole);

    }


}
