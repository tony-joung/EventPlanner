package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.role.IRoleDAO;
import com.example.eventmanagementdemo.dao.role.RoleDAOImpl;
import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.dao.user.UserDAOImpl;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import com.example.eventmanagementdemo.utils.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static com.example.eventmanagementdemo.utils.Utils.*;

public class RegisterUserController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<Role> roleComboBox;

    private IUserDAO userDAO;
    IRoleDAO roleDAO;

    public RegisterUserController() {
        Connection connection = SqliteConnection.getInstance();
        this.userDAO = new UserDAOImpl(connection);
        this.roleDAO = new RoleDAOImpl(connection);
    }

    @FXML
    public void initialize() {
        loadRoles();
    }

    private void loadRoles() {
        List<Role> roles = roleDAO.getAllRoles();
        ObservableList<Role> roleList = FXCollections.observableArrayList(roles);
        roleComboBox.setItems(roleList);
    }

    @FXML
    private void handleBack(){
        navigateToLoginPage();
    }

    @FXML
    void handleRegister() {
        String username = getUsername().orElse("");
        String password = getPassword().orElse("");
        Role selectedRole = roleComboBox.getValue();

        if (isAnyFieldEmpty(username, password, selectedRole)) {
            showError("Error", "Please fill all fields.");
            return;
        }

        if (!isValidPassword(password)) {
            showError("Error", "Password must contain at least one uppercase letter and one special character.");
            return;
        }

        if (userAlreadyExists(username)) {
            showError("Error", "User Name Already Exists");
            usernameTextField.requestFocus();
            return;
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        User newUser = new User(0, username, hashedPassword, selectedRole);
        if (saveUser(newUser)) {
            showSuccess("Success", "User registered successfully.", this::navigateToLoginPage);
        } else {
            showError("Error", "Failed to register user.");
        }
    }

    private Optional<String> getUsername() {
        return Optional.ofNullable(usernameTextField.getText()).map(String::toLowerCase);
    }

    private Optional<String> getPassword() {
        return Optional.ofNullable(passwordField.getText());
    }

    private Optional<Role> getSelectedRole() {
        return Optional.ofNullable(roleComboBox.getValue());
    }

    private boolean isAnyFieldEmpty(String username, String password, Role selectedRole) {
        return username.isEmpty() || password.isEmpty() || selectedRole == null;
    }

    private boolean userAlreadyExists(String username) {
        return userDAO.getUserByUsername(username).isPresent();
    }

    private boolean saveUser(User user) {
        return userDAO.saveUser(user);
    }

    private void navigateToLoginPage() {
        navigateTo(roleComboBox, "login-page.fxml");
    }

    public static boolean isValidPassword(String password) {
        // Regex for at least one uppercase letter and one special character
        String regex = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$";

        // Check if the password matches the regex
        return password != null && password.matches(regex);
    }

}
