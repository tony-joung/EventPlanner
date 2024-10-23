package com.example.eventmanagementdemo.sqlite;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SqliteConnectionTest {

    @Test
    public void testGetInstanceReturnsNonNullConnection() {
        // Retrieve the singleton instance
        Connection connection = SqliteConnection.getInstance();

        // Assert that the connection is not null
        assertNotNull(connection, "The connection should not be null");
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        // Retrieve the singleton instance twice
        Connection firstInstance = SqliteConnection.getInstance();
        Connection secondInstance = SqliteConnection.getInstance();

        // Assert that both instances are the same (singleton pattern)
        assertSame(firstInstance, secondInstance, "Both instances should be the same");
    }

    @Test
    public void testConnectionIsValid() {
        // Retrieve the singleton instance
        Connection connection = SqliteConnection.getInstance();

        try {
            // Check if the connection is valid (assuming a timeout of 5 seconds)
            boolean isValid = connection.isValid(5);
            assertTrue(isValid, "The connection should be valid");
        } catch (SQLException exception) {
            fail("The connection validity check should not throw an exception");
        }
    }
}