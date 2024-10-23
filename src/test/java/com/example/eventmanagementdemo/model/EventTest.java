package com.example.eventmanagementdemo.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    private Event event;

    @BeforeEach
    public void setUp() {
        // Initialize an Event object before each test
        event = new Event("Concert", "Music Group", LocalDate.of(2023, 12, 25), "Concert Hall", "1234567890");
    }

    @Test
    public void testGetEventName() {
        assertEquals("Concert", event.getEventName(), "Event name should be 'Concert'");
    }

    @Test
    public void testSetEventName() {
        event.setEventName("Festival");
        assertEquals("Festival", event.getEventName(), "Event name should be 'Festival'");
    }

    @Test
    public void testGetHostedBy() {
        assertEquals("Music Group", event.getHostedBy(), "Hosted by should be 'Music Group'");
    }

    @Test
    public void testSetHostedBy() {
        event.setHostedBy("New Host");
        assertEquals("New Host", event.getHostedBy(), "Hosted by should be 'New Host'");
    }

    @Test
    public void testGetDate() {
        assertEquals(LocalDate.of(2023, 12, 25), event.getDate(), "Date should be '2023-12-25'");
    }

    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2024, 1, 1);
        event.setDate(newDate);
        assertEquals(newDate, event.getDate(), "Date should be '2024-01-01'");
    }

    @Test
    public void testGetVenue() {
        assertEquals("Concert Hall", event.getVenue(), "Venue should be 'Concert Hall'");
    }

    @Test
    public void testSetVenue() {
        event.setVenue("New Venue");
        assertEquals("New Venue", event.getVenue(), "Venue should be 'New Venue'");
    }

    @Test
    public void testGetPhone() {
        assertEquals("1234567890", event.getPhone(), "Phone should be '1234567890'");
    }

    @Test
    public void testSetPhone() {
        event.setPhone("0987654321");
        assertEquals("0987654321", event.getPhone(), "Phone should be '0987654321'");
    }

    @Test
    public void testGetId() {
        assertEquals(0, event.getId(), "Initial ID should be 0");
    }

    @Test
    public void testSetId() {
        event.setId(10);
        assertEquals(10, event.getId(), "ID should be 10");
    }
}
