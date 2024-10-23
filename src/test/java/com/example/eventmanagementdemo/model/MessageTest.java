package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    private Message messageWithoutId;
    private Message messageWithId;
    private LocalDateTime dateTime;

    @BeforeEach
    public void setUp() {
        dateTime = LocalDateTime.of(2023, 10, 10, 15, 30);
        messageWithoutId = new Message("sender@example.com", "receiver@example.com", "Subject", dateTime, "This is a test message.");
        messageWithId = new Message(1, "sender@example.com", "receiver@example.com", "Subject", dateTime, "This is a test message.");
    }

    @Test
    public void testConstructorWithoutId() {
        assertEquals("sender@example.com", messageWithoutId.getSender(), "Sender should be initialized correctly");
        assertEquals("receiver@example.com", messageWithoutId.getReceiver(), "Receiver should be initialized correctly");
        assertEquals("Subject", messageWithoutId.getSubject(), "Subject should be initialized correctly");
        assertEquals(dateTime, messageWithoutId.getDatetime(), "Datetime should be initialized correctly");
        assertEquals("This is a test message.", messageWithoutId.getMessage(), "Message should be initialized correctly");
    }

    @Test
    public void testConstructorWithId() {
        assertEquals(1, messageWithId.getId(), "ID should be initialized correctly");
        assertEquals("sender@example.com", messageWithId.getSender(), "Sender should be initialized correctly");
        assertEquals("receiver@example.com", messageWithId.getReceiver(), "Receiver should be initialized correctly");
        assertEquals("Subject", messageWithId.getSubject(), "Subject should be initialized correctly");
        assertEquals(dateTime, messageWithId.getDatetime(), "Datetime should be initialized correctly");
        assertEquals("This is a test message.", messageWithId.getMessage(), "Message should be initialized correctly");
    }

    @Test
    public void testSettersAndGetters() {
        messageWithId.setId(2);
        assertEquals(2, messageWithId.getId(), "ID should be updated correctly");

        messageWithId.setSender("new_sender@example.com");
        assertEquals("new_sender@example.com", messageWithId.getSender(), "Sender should be updated correctly");

        messageWithId.setReceiver("new_receiver@example.com");
        assertEquals("new_receiver@example.com", messageWithId.getReceiver(), "Receiver should be updated correctly");

        messageWithId.setSubject("New Subject");
        assertEquals("New Subject", messageWithId.getSubject(), "Subject should be updated correctly");

        LocalDateTime newDateTime = LocalDateTime.of(2023, 11, 11, 16, 45);
        messageWithId.setDatetime(newDateTime);
        assertEquals(newDateTime, messageWithId.getDatetime(), "Datetime should be updated correctly");

        messageWithId.setMessage("This is a new test message.");
        assertEquals("This is a new test message.", messageWithId.getMessage(), "Message should be updated correctly");
    }
}
