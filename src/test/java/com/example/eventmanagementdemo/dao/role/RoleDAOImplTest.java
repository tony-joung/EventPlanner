package com.example.eventmanagementdemo.dao.role;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Role;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


public class RoleDAOImplTest {

    private Connection connection;
    private RoleDAOImpl roleDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.getInstance();
        Statement stmt = connection.createStatement();

        // Create the roles table
        stmt.execute("CREATE TABLE roles (id INT PRIMARY KEY, name VARCHAR(255))");

        // Insert sample roles
        stmt.execute("INSERT INTO roles (id, name) VALUES (1, 'Host')");
        stmt.execute("INSERT INTO roles (id, name) VALUES (2, 'Participant')");
        stmt.execute("INSERT INTO roles (id, name) VALUES (3, 'Guest')");

        // Initialize RoleDAO
        roleDAO = new RoleDAOImpl(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up the database
        connection.createStatement().execute("DROP TABLE roles");
    }


    @Test
    public void testGetRoleByName() {
        // Retrieve a role by name
        Optional<Role> roleOpt = roleDAO.getRoleByName("Host");

        // Verify the role is found and has correct attributes
        assertTrue(roleOpt.isPresent(), "Role should be found");
        Role role = roleOpt.get();
        assertEquals(1, role.getId(), "Role ID should be 1");
        assertEquals("Host", role.getName(), "Role name should be 'Host'");
    }

    @Test
    public void testGetRoleByNameNotFound() {
        // Attempt to retrieve a non-existent role
        Optional<Role> roleOpt = roleDAO.getRoleByName("NonExistentRole");

        // Verify the role is not found
        assertFalse(roleOpt.isPresent(), "Role should not be found");
    }

    @Test
    public void testGetRoleById() {
        // Retrieve a role by ID
        Optional<Role> roleOpt = roleDAO.getRoleById(2);

        // Verify the role is found and has correct attributes
        assertTrue(roleOpt.isPresent(), "Role should be found");
        Role role = roleOpt.get();
        assertEquals(2, role.getId(), "Role ID should be 2");
        assertEquals("Participant", role.getName(), "Role name should be 'Participant'");
    }

    @Test
    public void testGetRoleByIdNotFound() {
        // Attempt to retrieve a role with a non-existent ID
        Optional<Role> roleOpt = roleDAO.getRoleById(99);

        // Verify the role is not found
        assertFalse(roleOpt.isPresent(), "Role should not be found");
    }

    @Test
    public void testGetAllRoles() {
        // Retrieve all roles
        List<Role> roles = roleDAO.getAllRoles();

        // Verify all roles are retrieved
        assertEquals(3, roles.size(), "There should be 3 roles");
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Host")), "Role 'Host' should be present");
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Participant")), "Role 'Participant' should be present");
        assertTrue(roles.stream().anyMatch(role -> role.getName().equals("Guest")), "Role 'Guest' should be present");
    }
}
