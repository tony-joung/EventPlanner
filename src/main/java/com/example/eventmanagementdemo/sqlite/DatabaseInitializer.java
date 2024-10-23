package com.example.eventmanagementdemo.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final Connection connection = SqliteConnection.getInstance();

    public static void initializeDatabase() {

        try {
            Statement statement = connection.createStatement();

            //Creating events table
            String query = "CREATE TABLE IF NOT EXISTS events ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "eventName VARCHAR NOT NULL,"
                    + "hostedBy VARCHAR NOT NULL,"
                    + "date DATE NOT NULL,"
                    + "venue VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);

            // Create roles table
            String createRolesTable = "CREATE TABLE IF NOT EXISTS roles ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL UNIQUE"
                    + ");";
            statement.execute(createRolesTable);

            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL,"
                    + "role_id INTEGER,"
                    + "FOREIGN KEY (role_id) REFERENCES roles (id)"
                    + ");";
            statement.execute(createUsersTable);


            //Create message table
            String createMessageTable = "CREATE TABLE IF NOT EXISTS messages ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "sender TEXT NOT NULL,"
                    + "receiver TEXT NOT NULL,"
                    + "subject TEXT NOT NULL,"
                    + "message TEXT,"
                    + "datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP"
                    + ");";

            statement.execute(createMessageTable);

            // Create tickets table

            String createTicketsTable = "CREATE TABLE IF NOT EXISTS tickets ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "eventId INTEGER NOT NULL,"
                    + "type TEXT NOT NULL,"
                    + "count INTEGER NOT NULL DEFAULT 0,"
                    + "available INTEGER ,"
                    + "price REAL NOT NULL,"
                    + "FOREIGN KEY (eventId) REFERENCES events (id)"
                    + ");";

            statement.execute(createTicketsTable);

            // Create bookings table

            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "eventId INTEGER NOT NULL,"
                    + "ticketId INTEGER NOT NULL,"
                    + "userId INTEGER NOT NULL,"
                    + "quantity INTEGER NOT NULL,"
                    + "bookingDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (eventId) REFERENCES events (id),"
                    + "FOREIGN KEY (ticketId) REFERENCES tickets (id),"
                    + "FOREIGN KEY (userId) REFERENCES users (id)"
                    + ");";

            statement.execute(createBookingsTable);

            // Insert default roles
            insertDefaultRoles(connection);
            System.out.println("Database tables initialized successfully.");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error initializing database: " + exception.getMessage());
        }
    }
    private static void insertDefaultRoles(Connection connection) {
        String[] defaultRoles = {"Host", "Participant"};
        String insertRoleSQL = "INSERT OR IGNORE INTO roles (name) VALUES (?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertRoleSQL)) {
            for (String role : defaultRoles) {
                pstmt.setString(1, role);
                pstmt.executeUpdate();
            }
            System.out.println("Default roles inserted successfully.");
        } catch (SQLException exception) {
            System.out.println("Error inserting default roles: " + exception.getMessage());
        }
    }
}
