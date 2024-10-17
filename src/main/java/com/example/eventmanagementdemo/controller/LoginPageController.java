package com.example.eventmanagementdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    private TextField UserNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private void initialize() {
        // Initialize the ComboBox with roles
        roleComboBox.getItems().addAll("Host", "Participant");
        roleComboBox.setValue("Host"); // Set default selection
    }

    @FXML
    private void handleSignIn() {
        String username = UserNameTextField.getText();
        String password = passwordTextField.getText();
        String role = roleComboBox.getValue();

        // Implemention of login logic
        System.out.println("Logging in as " + role + " with username: " + username);

        // Example: Perform validation and proceed with login
        if (validateCredentials(username, password, role)) {
            // Navigate to the next screen or show a success message
            System.out.println("Login successful!");
        } else {
            // Show an error message
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean validateCredentials(String username, String password, String role) {
        // Basic validation for empty field.
        return !username.isEmpty() && !password.isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRegister() {
        System.out.println("Redirecting to registration page...");
    }
}
