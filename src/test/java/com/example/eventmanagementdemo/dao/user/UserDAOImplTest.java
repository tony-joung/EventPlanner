package com.example.eventmanagementdemo.dao.user;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;


public class UserDAOImplTest {

    private Connection connection;
    private UserDAOImpl userDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Use an in-memory sqllite database for testing
        connection = SqliteConnection.getInstance();
        Statement stmt = connection.createStatement();

        // Create roles and users tables
        stmt.execute("CREATE TABLE roles (id INT PRIMARY KEY, name VARCHAR(255))");
        stmt.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), role_id INT, FOREIGN KEY (role_id) REFERENCES roles(id))");

        // Insert a sample role
        stmt.execute("INSERT INTO roles (id, name) VALUES (1, 'Host')");

        // Initialize UserDAO
        userDAO = new UserDAOImpl(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up the database
        connection.createStatement().execute("DROP TABLE users");
        connection.createStatement().execute("DROP TABLE roles");
    }

    @Test
    public void testGetUserByUsername() throws SQLException {
        // Insert a sample user
        connection.createStatement().execute("INSERT INTO users (username, password, role_id) VALUES ('testUser', 'password123', 1)");

        // Retrieve the user
        Optional<User> userOpt = userDAO.getUserByUsername("testUser");

        // Verify the user is found and has correct attributes
        assertTrue(userOpt.isPresent(), "User should be found");
        User user = userOpt.get();
        assertEquals("testUser", user.getUsername(), "Username should be 'testUser'");
        assertEquals("password123", user.getPassword(), "Password should be 'password123'");
        assertEquals(1, user.getRole().getId(), "Role ID should be 1");
        assertEquals("Host", user.getRole().getName(), "Role name should be 'Host'");
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        // Attempt to retrieve a non-existent user
        Optional<User> userOpt = userDAO.getUserByUsername("nonExistentUser");

        // Verify the user is not found
        assertFalse(userOpt.isPresent(), "User should not be found");
    }

    @Test
    public void testSaveUser() {
        // Create a new user
        Role role = new Role(1, "Host");
        User newUser = new User(0, "newUser", "newPassword", role);

        // Save the user
        boolean success = userDAO.saveUser(newUser);

        // Verify the user is saved successfully
        assertTrue(success, "User should be saved successfully");

        // Retrieve the user and verify
        Optional<User> userOpt = userDAO.getUserByUsername("newUser");
        assertTrue(userOpt.isPresent(), "Saved user should be found");
        User user = userOpt.get();
        assertEquals("newUser", user.getUsername(), "Username should be 'newUser'");
        assertEquals("newPassword", user.getPassword(), "Password should be 'newPassword'");
    }
}
