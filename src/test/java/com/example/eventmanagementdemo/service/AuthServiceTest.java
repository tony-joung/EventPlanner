package com.example.eventmanagementdemo.service;


import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.utils.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private IUserDAO userDAO;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userDAO);
    }

    @Test
    public void testLoginSuccess() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String roleName = "Host";

        User mockUser = new User(1,username, PasswordHasher.hashPassword(password), new Role(1,roleName));
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = authService.login(username, password, roleName);

        // Assert
        assertTrue(result, "The login should succeed with correct username, password, and role.");
    }

    @Test
    public void testLoginFailureDueToIncorrectPassword() {
        // Arrange
        String username = "testUser";
        String correctPassword = "correctPassword";
        String incorrectPassword = "incorrectPassword";
        String roleName = "Host";

        User mockUser = new User(1,username, PasswordHasher.hashPassword(correctPassword), new Role(1,roleName));
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = authService.login(username, incorrectPassword, roleName);

        // Assert
        assertFalse(result, "The login should fail with incorrect password.");
    }

    @Test
    public void testLoginFailureDueToIncorrectRole() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String correctRoleName = "Host";
        String incorrectRoleName = "User";

        User mockUser = new User(1,username, PasswordHasher.hashPassword(password), new Role(1,correctRoleName));
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = authService.login(username, password, incorrectRoleName);

        // Assert
        assertFalse(result, "The login should fail with incorrect role.");
    }

    @Test
    public void testLoginFailureDueToNonexistentUser() {
        // Arrange
        String username = "nonexistentUser";
        String password = "testPassword";
        String roleName = "Host";

        when(userDAO.getUserByUsername(username)).thenReturn(Optional.empty());

        // Act
        boolean result = authService.login(username, password, roleName);

        // Assert
        assertFalse(result, "The login should fail if the user does not exist.");
    }
}
