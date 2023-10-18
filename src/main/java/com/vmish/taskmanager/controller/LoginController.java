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

/**
 * Окно для создания пользователя и авторизации
 */
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

    /**
     * Метод для ввода логина и пароля и поиска пользователя в базе данных
     * @return возвращает авторизационный токен в случае нахождения пользователя в базе и успешной проверки пароля
     */
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

    /**
     * проверяет заполнены ли поля в окне
     * @param checkedUser Пользователь найденный в базе или null
     * @return true если пользователь есть, пароль совпадает
     */
    private boolean isAuthenticated(Optional<MyUser> checkedUser) {
        return checkedUser.isPresent() && checkedUser.get().getPassword().equals(passwordField.getText().trim());
    }

    /**
     * закрытие окна по нажатию на кнопку Cancel
     */
    public void cancel() {
        stage.close();
    }

    /**
     * Вызываемый метод из главного окна.
     * @return возвращает токен или null
     */
    public Auth getAuth() {
        stage.showAndWait();
        return login();
    }

    /**
     * Модифицирует окно добавляя строку с ролью, метод нужен для тестирования разных пользователей, в проме такой функции нет
     */
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

    /**
     * Проверяет что все поля заполнены
     * @param rolesComboBox поле выбора роли
     * @return true если все поля заполнены
     */
    private boolean isFieldsAreFieldusernameTextField(ComboBox rolesComboBox) {
        return !(usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || rolesComboBox.getValue() == null);
    }

    /**
     * Метод для модифицирования окна чтобы в окно авторизации добавилось поле выбора роли для тестирования приложения
     * @param rolesComboBox требуется чтобы не делать глобавльную переменную
     * @param isShow если true добавляем поля, если false прячем.
     */
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
