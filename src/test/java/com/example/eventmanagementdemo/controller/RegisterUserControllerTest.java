package com.example.eventmanagementdemo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.dao.role.IRoleDAO;
import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserControllerTest {

    @Mock
    private IUserDAO userDAO;

    @Mock
    private IRoleDAO roleDAO;

    private TextField usernameTextField = new TextField();

    @Mock
    private PasswordField passwordField;

    @Mock
    private ComboBox<Role> roleComboBox;

    @InjectMocks
    private RegisterUserController controller;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them
    }

    @Test
    public void testHandleRegisterWithEmptyFields() {
        // Arrange
        when(usernameTextField.getText()).thenReturn("");
        when(passwordField.getText()).thenReturn("");
        when(roleComboBox.getValue()).thenReturn(null);

        // Act
        controller.handleRegister();

        // Assert
        verify(usernameTextField).requestFocus();
    }

    @Test
    public void testHandleRegisterWithInvalidPassword() {
        // Arrange
        when(usernameTextField.getText()).thenReturn("testUser");
        when(passwordField.getText()).thenReturn("password"); // Invalid password
        when(roleComboBox.getValue()).thenReturn(new Role(1, "User"));

        // Act
        controller.handleRegister();

        // Assert
        verify(usernameTextField).requestFocus();
    }

    @Test
    public void testHandleRegisterWithExistingUser() {
        // Arrange
        when(usernameTextField.getText()).thenReturn("existingUser");
        when(passwordField.getText()).thenReturn("Password@1");
        when(roleComboBox.getValue()).thenReturn(new Role(1, "User"));
        when(userDAO.getUserByUsername("existinguser")).thenReturn(Optional.of(new User(1, "existingUser", "password", new Role(1, "User"))));

        // Act
        controller.handleRegister();

        // Assert
        verify(usernameTextField).requestFocus();
    }

    @Test
    public void testHandleRegisterSuccess() {
        // Arrange
        when(usernameTextField.getText()).thenReturn("newUser");
        when(passwordField.getText()).thenReturn("Password@1");
        when(roleComboBox.getValue()).thenReturn(new Role(1, "User"));
        when(userDAO.getUserByUsername("newuser")).thenReturn(Optional.empty());
        when(userDAO.saveUser(any(User.class))).thenReturn(true);

        // Act
        controller.handleRegister();

        // Assert
        verify(userDAO).saveUser(any(User.class));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(RegisterUserController.isValidPassword("Password@1"), "Password should be valid");
        assertFalse(RegisterUserController.isValidPassword("password"), "Password should be invalid");
        assertFalse(RegisterUserController.isValidPassword("PASSWORD"), "Password should be invalid");
    }

    @Test
    public void testLoadRoles() {
        // Arrange
        List<Role> roles = Arrays.asList(new Role(1, "Admin"), new Role(2, "User"));
        when(roleDAO.getAllRoles()).thenReturn(roles);

        // Act
        controller.initialize();

        // Assert
        ObservableList<Role> expectedRoles = FXCollections.observableArrayList(roles);
        verify(roleComboBox).setItems(expectedRoles);
    }
}
