package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.role.IRoleDAO;
import com.example.eventmanagementdemo.dao.role.RoleDAOImpl;
import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.dao.user.UserDAOImpl;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.service.AuthService;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.*;

public class LoginPageController {

    @FXML
    private TextField UserNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private ComboBox<Role> roleComboBox;

    private final IRoleDAO roleDAO;

    private IUserDAO userDAO;

    private final AuthService authService;

    public LoginPageController() {
        Connection connection = SqliteConnection.getInstance();
        this.roleDAO = new RoleDAOImpl(connection);
        this.userDAO = new UserDAOImpl(connection);
        this.authService = new AuthService(userDAO);
    }

    @FXML
    private void initialize() {
        List<Role> roles = roleDAO.getAllRoles();
        ObservableList<Role> roleList = FXCollections.observableArrayList(roles);
        roleComboBox.setItems(roleList);
        roleComboBox.setValue(roles.getFirst()); // Set default selection
    }

    @FXML
    private void handleSignIn() {
        String username = UserNameTextField.getText().toLowerCase();
        String password = passwordTextField.getText();
        Role role = roleComboBox.getValue();

        System.out.println("Logging in as " + role + " with username: " + username);
        if(!validateCredentials(username,password,role.getName())) {
            showError("Input Error", "Invalid username or password.");
        }

        if (authService.login(username, password, role.getName())) {
            // Navigate to the next screen or show a success message
            System.out.println("Login successful!");
            User user = userDAO.getUserByUsername(username).get();
            Consumer<FXMLLoader> loader = (fxmlLoader) -> {
                MenuPageController menuPageController = fxmlLoader.getController();
                menuPageController.initData(user);
            };
            navigateWithInitialData(roleComboBox, "menu/menu-page.fxml", "menu/menu.css",loader);
        } else {
            // Show an error message
            showError("Login Failed", "Invalid username or password.");
        }
    }

    private boolean validateCredentials(String username, String password, String role) {
        // Basic validation for empty field.
        return !username.isEmpty() && !password.isEmpty();
    }

    @FXML
    private void handleRegister() throws IOException {
        navigateTo(roleComboBox,"register-page.fxml");
        System.out.println("Redirecting to registration page...");
    }
}
