package com.example.eventmanagementdemo.dao.message;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Message;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDAOImplTest {

    private Connection connection;
    private MessageDAOImpl messageDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.getInstance();

        // Initialize the DAO
        messageDAO = new MessageDAOImpl(connection);

        // Create the necessary table for testing
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sender TEXT, receiver TEXT, subject TEXT, " +
                    "datetime DATETIME, message TEXT)");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE messages");
        }
    }

    @Test
    public void testInsertMessage() {
        Message message = new Message("sender@example.com", "receiver@example.com", "Subject", LocalDateTime.now(), "This is a test message.");
        messageDAO.insertMessage(message);

        List<Message> messages = messageDAO.getAllMessages();
        assertEquals(1, messages.size(), "Should have one message in the database");
    }

    @Test
    public void testGetAllMessages() {
        Message message1 = new Message("sender1@example.com", "receiver1@example.com", "Subject 1", LocalDateTime.now(), "Message 1");
        Message message2 = new Message("sender2@example.com", "receiver2@example.com", "Subject 2", LocalDateTime.now().minusDays(1), "Message 2");
        messageDAO.insertMessage(message1);
        messageDAO.insertMessage(message2);

        List<Message> messages = messageDAO.getAllMessages();
        assertEquals(2, messages.size(), "Should have two messages in the database");
        assertEquals("Subject 1", messages.get(0).getSubject(), "The most recent message should be first");
    }

    @Test
    public void testGetMessageById() {
        Message message = new Message("sender@example.com", "receiver@example.com", "Subject", LocalDateTime.now(), "This is a test message.");
        messageDAO.insertMessage(message);

        Message retrievedMessage = messageDAO.getMessageById(1);
        assertNotNull(retrievedMessage, "Message should not be null");
        assertEquals("Subject", retrievedMessage.getSubject(), "Subject should match");
    }

    @Test
    public void testDeleteMessage() {
        Message message = new Message("sender@example.com", "receiver@example.com", "Subject", LocalDateTime.now(), "This is a test message.");
        messageDAO.insertMessage(message);

        messageDAO.deleteMessage(1);

        Message retrievedMessage = messageDAO.getMessageById(1);
        assertNull(retrievedMessage, "Message should be null after deletion");
    }

    @Test
    public void testGetMessagesByReceiver() {
        Message message1 = new Message("sender1@example.com", "receiver@example.com", "Subject 1", LocalDateTime.now(), "Message 1");
        Message message2 = new Message("sender2@example.com", "receiver@example.com", "Subject 2", LocalDateTime.now().minusDays(1), "Message 2");
        Message message3 = new Message("sender3@example.com", "other_receiver@example.com", "Subject 3", LocalDateTime.now().minusDays(2), "Message 3");
        messageDAO.insertMessage(message1);
        messageDAO.insertMessage(message2);
        messageDAO.insertMessage(message3);

        List<Message> messages = messageDAO.getMessagesByReceiver("receiver@example.com");
        assertEquals(2, messages.size(), "Should have two messages for the specified receiver");
        assertEquals("Subject 1", messages.get(0).getSubject(), "The most recent message should be first");
    }
}
