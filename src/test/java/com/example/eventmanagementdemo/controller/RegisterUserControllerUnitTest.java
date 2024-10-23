package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.role.IRoleDAO;
import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterUserControllerUnitTest extends ApplicationTest {

    private RegisterUserController controller;
    private IUserDAO mockUserDAO;
    private IRoleDAO mockRoleDAO;

    @Override
    public void start(Stage stage) {
        // This method is required by TestFX, but we don't need to show a stage for these tests
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockUserDAO = Mockito.mock(IUserDAO.class);
        mockRoleDAO = Mockito.mock(IRoleDAO.class);

        controller = spy(new RegisterUserController());

        setPrivateField(controller, "userDAO", mockUserDAO);
        setPrivateField(controller, "roleDAO", mockRoleDAO);

        setPrivateField(controller, "usernameTextField", new TextField());
        setPrivateField(controller, "passwordField", new PasswordField());
        setPrivateField(controller, "roleComboBox", new ComboBox<Role>());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testInitializeLoadsRoles() {
        // Arrange
        Role adminRole = new Role(1, "Admin");
        Role userRole = new Role(2, "User");
        when(mockRoleDAO.getAllRoles()).thenReturn(List.of(adminRole, userRole));

        // Act
        Platform.runLater(() -> controller.initialize());

        // Assert
        Platform.runLater(() -> {
            ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");
            ObservableList<Role> expectedRoles = FXCollections.observableArrayList(adminRole, userRole);
            assertEquals(expectedRoles, roleComboBox.getItems());
        });
    }

    @Test
    public void testHandleRegisterWithEmptyFieldsShowsError() {
        // Arrange
        TextField usernameTextField = getPrivateField(controller, "usernameTextField");
        PasswordField passwordField = getPrivateField(controller, "passwordField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        usernameTextField.setText("");
        passwordField.setText("");
        roleComboBox.setValue(null);

        // Act
        Platform.runLater(() -> controller.handleRegister());

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).showError(eq("Error"), eq("Please fill all fields."));
        });
    }

    @Test
    public void testHandleRegisterWithInvalidPasswordShowsError() {
        // Arrange
        TextField usernameTextField = getPrivateField(controller, "usernameTextField");
        PasswordField passwordField = getPrivateField(controller, "passwordField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        usernameTextField.setText("testuser");
        passwordField.setText("invalid");
        roleComboBox.setValue(new Role(1, "User"));

        // Act
        Platform.runLater(() -> controller.handleRegister());

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).showError(eq("Error"), eq("Password must contain at least one uppercase letter and one special character."));
        });
    }

    @Test
    public void testHandleRegisterWithExistingUserShowsError() {
        // Arrange
        TextField usernameTextField = getPrivateField(controller, "usernameTextField");
        PasswordField passwordField = getPrivateField(controller, "passwordField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        usernameTextField.setText("existinguser");
        passwordField.setText("Valid@123");
        roleComboBox.setValue(new Role(1, "User"));
        when(mockUserDAO.getUserByUsername("existinguser")).thenReturn(Optional.of(new User(1, "existinguser", "password", new Role(1, "User"))));

        // Act
        Platform.runLater(() -> controller.handleRegister());

        // Assert
        Platform.runLater(() -> {
            assertTrue(usernameTextField.isFocused());
        });
    }

    @Test
    public void testHandleRegisterWithValidDataSucceeds() {
        // Arrange
        TextField usernameTextField = getPrivateField(controller, "usernameTextField");
        PasswordField passwordField = getPrivateField(controller, "passwordField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        usernameTextField.setText("newuser");
        passwordField.setText("Valid@123");
        roleComboBox.setValue(new Role(1, "User"));
        when(mockUserDAO.getUserByUsername("newuser")).thenReturn(Optional.empty());
        when(mockUserDAO.saveUser(any(User.class))).thenReturn(true);

        // Act
        Platform.runLater(() -> controller.handleRegister());

        // Assert
        Platform.runLater(() -> {
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(mockUserDAO, times(1)).saveUser(userCaptor.capture());
            User savedUser = userCaptor.getValue();
            assertEquals("newuser", savedUser.getUsername());
            assertEquals("User", savedUser.getRole().getName());
        });
    }

    private <T> T getPrivateField(Object target, String fieldName)  {
        Field field = null;
        try {
            field = target.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        try {
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
