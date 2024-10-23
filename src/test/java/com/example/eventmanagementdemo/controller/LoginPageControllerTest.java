/*
package com.example.eventmanagementdemo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.dao.role.IRoleDAO;
import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.service.AuthService;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginPageControllerTest extends ApplicationTest {

    private LoginPageController controller;
    private IRoleDAO mockRoleDAO;
    private IUserDAO mockUserDAO;
    private AuthService mockAuthService;

    @Override
    public void start(Stage stage) {
        // This method is required by TestFX, but we don't need to show a stage for these tests
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockRoleDAO = Mockito.mock(IRoleDAO.class);
        mockUserDAO = Mockito.mock(IUserDAO.class);
        mockAuthService = Mockito.mock(AuthService.class);

        controller = new LoginPageController();

        setPrivateField(controller, "roleDAO", mockRoleDAO);
        setPrivateField(controller, "userDAO", mockUserDAO);
        setPrivateField(controller, "authService", mockAuthService);
        setPrivateField(controller, "UserNameTextField", new TextField());
        setPrivateField(controller, "passwordTextField", new PasswordField());
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
            assertEquals(adminRole, roleComboBox.getValue());
        });
    }

    @Test
    public void testHandleSignInWithEmptyFieldsShowsError() {
        // Arrange
        TextField userNameTextField = getPrivateField(controller, "UserNameTextField");
        PasswordField passwordTextField = getPrivateField(controller, "passwordTextField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        userNameTextField.setText("");
        passwordTextField.setText("");
        roleComboBox.setValue(new Role(1, "Admin"));

        // Act
        Platform.runLater(() -> controller.handleSignIn());

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).showError("Input Error", "Invalid username or password.");
        });
    }

    @Test
    public void testHandleSignInWithInvalidCredentialsShowsError() {
        // Arrange
        TextField userNameTextField = getPrivateField(controller, "UserNameTextField");
        PasswordField passwordTextField = getPrivateField(controller, "passwordTextField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        userNameTextField.setText("testuser");
        passwordTextField.setText("wrongpassword");
        roleComboBox.setValue(new Role(1, "Admin"));

        when(mockAuthService.login("testuser", "wrongpassword", "Admin")).thenReturn(false);

        // Act
        Platform.runLater(() -> controller.handleSignIn());

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).showError("Login Failed", "Invalid username or password.");
        });
    }

    @Test
    public void testHandleSignInWithValidCredentialsNavigatesToCorrectPage() {
        // Arrange
        TextField userNameTextField = getPrivateField(controller, "UserNameTextField");
        PasswordField passwordTextField = getPrivateField(controller, "passwordTextField");
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        userNameTextField.setText("validuser");
        passwordTextField.setText("validpassword");
        Role adminRole = new Role(1, "Admin");
        roleComboBox.setValue(adminRole);

        when(mockAuthService.login("validuser", "validpassword", "Admin")).thenReturn(true);

        // Act
        Platform.runLater(() -> controller.handleSignIn());

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).navigateTo(roleComboBox, "event-page.fxml");
        });
    }

    @Test
    public void testHandleRegisterNavigatesToRegisterPage() throws IOException {
        // Arrange
        ComboBox<Role> roleComboBox = getPrivateField(controller, "roleComboBox");

        // Act
        Platform.runLater(() -> {
            try {
                controller.handleRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Assert
        Platform.runLater(() -> {
//            verify(controller, times(1)).navigateTo(roleComboBox, "register-page.fxml");
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
*/
