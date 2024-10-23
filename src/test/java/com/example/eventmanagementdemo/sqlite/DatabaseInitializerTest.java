package com.example.eventmanagementdemo.sqlite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseInitializerTest {

    private Connection connection;

    @BeforeEach
    public void setUp() {
        // Set up the connection to use an in-memory database
        connection = SqliteConnection.getInstance();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up the database
        connection.createStatement().execute("DROP TABLE users");
        connection.createStatement().execute("DROP TABLE roles");
        connection.createStatement().execute("DROP TABLE events");
    }

    @Test
    public void testInitializeDatabaseCreatesTables() {
        // Initialize the database
        DatabaseInitializer.initializeDatabase();

        // Verify that the tables were created
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='events'");
            assertTrue(rs.next(), "The 'events' table should exist");

            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='roles'");
            assertTrue(rs.next(), "The 'roles' table should exist");

            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
            assertTrue(rs.next(), "The 'users' table should exist");
        } catch (SQLException exception) {
            fail("An exception should not occur while verifying table creation: " + exception.getMessage());
        }
    }

    @Test
    public void testInitializeDatabaseInsertsDefaultRoles() {
        // Initialize the database
        DatabaseInitializer.initializeDatabase();

        // Verify that default roles were inserted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM roles");

            // Check if the default roles are present
            boolean hostFound = false;
            boolean participantFound = false;
            boolean guestFound = false;

            while (rs.next()) {
                String roleName = rs.getString("name");
                if (roleName.equals("Host")) {
                    hostFound = true;
                } else if (roleName.equals("Participant")) {
                    participantFound = true;
                }
            }

            assertTrue(hostFound, "Role 'Host' should be inserted");
            assertTrue(participantFound, "Role 'Participant' should be inserted");
        } catch (SQLException exception) {
            fail("An exception should not occur while verifying default roles: " + exception.getMessage());
        }
    }
}
