package com.example.eventmanagementdemo.sqlite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        connection.createStatement().execute("DROP TABLE IF EXISTS bookings");
        connection.createStatement().execute("DROP TABLE IF EXISTS tickets");
        connection.createStatement().execute("DROP TABLE IF EXISTS messages");
        connection.createStatement().execute("DROP TABLE IF EXISTS users");
        connection.createStatement().execute("DROP TABLE IF EXISTS roles");
        connection.createStatement().execute("DROP TABLE IF EXISTS events");
    }

    @Test
    public void testInitializeDatabaseCreatesTables() {
        // Initialize the database
        DatabaseInitializer.initializeDatabase();

        // Verify that the tables were created
        try (Statement stmt = connection.createStatement()) {
            assertTableExists(stmt, "events");
            assertTableExists(stmt, "roles");
            assertTableExists(stmt, "users");
            assertTableExists(stmt, "messages");
            assertTableExists(stmt, "tickets");
            assertTableExists(stmt, "bookings");
        } catch (SQLException exception) {
            fail("An exception should not occur while verifying table creation: " + exception.getMessage());
        }
    }

    private void assertTableExists(Statement stmt, String tableName) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'");
        assertTrue(rs.next(), "The '" + tableName + "' table should exist");
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
