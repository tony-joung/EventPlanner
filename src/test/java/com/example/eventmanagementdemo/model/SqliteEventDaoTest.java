package com.example.eventmanagementdemo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SqliteEventDaoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testConnection() {
        Connection conn = SqliteConnection.getInstance();
        assertEquals(true, conn != null);
    }

    @Test
    void addEvent() {
    }

    @Test
    void getEvents() {
    }

    @Test
    void getEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void updateEvent() {
    }
}