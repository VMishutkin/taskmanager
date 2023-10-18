package com.vmish.taskmanager.controller;

import com.vmish.taskmanager.model.Auth;
import com.vmish.taskmanager.model.MyUser;
import com.vmish.taskmanager.model.Role;
import com.vmish.taskmanager.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    @FXML
    private Label authenticationFailedLabel;


    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Auth login() {
        Optional<MyUser> checkedUser = userRepository.findByUsername(usernameTextField.getText());
        if (isAuthenticated(checkedUser)) {
            MyUser user = userRepository.findByUsername(usernameTextField.getText()).get();
            authenticationFailedLabel.setVisible(false);
            stage.close();
            return (new Auth(user.getUsername(), user.getRole()));
        } else {
            authenticationFailedLabel.setVisible(true);
        }
        return null;
    }

    private boolean isAuthenticated(Optional<MyUser> checkedUser) {
        return checkedUser.isPresent() && checkedUser.get().getPassword().equals(passwordField.getText().trim());
    }

    public void cancel() {
        stage.close();
    }

    public Auth getAuth() {
        stage.showAndWait();
        return login();
    }

    public void createUser() {
        ComboBox rolesComboBox = new ComboBox();
        showRoleBox(rolesComboBox, true);
        stage.showAndWait();
        MyUser newUser = new MyUser();
        if (isFieldsAreFieldusernameTextField(rolesComboBox)) {
            newUser = new MyUser(usernameTextField.getText(), passwordField.getText(),
                    Role.valueOf(rolesComboBox.getValue().toString()));
        }
        if (newUser.getUsername() != null) {
            userRepository.save(newUser);
        }
        showRoleBox(rolesComboBox, false);
    }

    private boolean isFieldsAreFieldusernameTextField(ComboBox rolesComboBox) {
        return !(usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || rolesComboBox.getValue() == null);
    }

    private void showRoleBox(ComboBox rolesComboBox, boolean isShow) {
        Label userRoleLabel = new Label("Роль");
        loginButton.setOnAction(v -> stage.close());
        if (isShow) {
            loginButton.setText("Save");
            List<String> roles = Stream.of(Role.values()).map(Enum::name).toList();
            rolesComboBox.getItems().addAll(roles);
            loginGridPane.add(rolesComboBox, 1, 4);
            loginGridPane.add(userRoleLabel, 0, 4);
            GridPane.setHalignment(userRoleLabel, HPos.RIGHT);
        } else {
            loginGridPane.getChildren().remove(rolesComboBox);
            loginGridPane.getChildren().remove(userRoleLabel);
            loginButton.setText("Login");
        }
    }


}
