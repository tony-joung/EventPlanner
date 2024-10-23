package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {

    private Ticket ticketWithoutId;
    private Ticket ticketWithId;

    @BeforeEach
    public void setUp() {
        ticketWithoutId = new Ticket(101, "VIP", 100, 50, 99.99);
        ticketWithId = new Ticket(1, 101, "VIP", 100, 50, 99.99);
    }

    @Test
    public void testConstructorWithoutId() {
        assertEquals(101, ticketWithoutId.getEventId(), "Event ID should be initialized correctly");
        assertEquals("VIP", ticketWithoutId.getType(), "Type should be initialized correctly");
        assertEquals(100, ticketWithoutId.getCount(), "Count should be initialized correctly");
        assertEquals(50, ticketWithoutId.getAvailable(), "Available should be initialized correctly");
        assertEquals(99.99, ticketWithoutId.getPrice(), "Price should be initialized correctly");
    }

    @Test
    public void testConstructorWithId() {
        assertEquals(1, ticketWithId.getId(), "ID should be initialized correctly");
        assertEquals(101, ticketWithId.getEventId(), "Event ID should be initialized correctly");
        assertEquals("VIP", ticketWithId.getType(), "Type should be initialized correctly");
        assertEquals(100, ticketWithId.getCount(), "Count should be initialized correctly");
        assertEquals(50, ticketWithId.getAvailable(), "Available should be initialized correctly");
        assertEquals(99.99, ticketWithId.getPrice(), "Price should be initialized correctly");
    }

    @Test
    public void testSettersAndGetters() {
        ticketWithId.setId(2);
        assertEquals(2, ticketWithId.getId(), "ID should be updated correctly");

        ticketWithId.setEventId(102);
        assertEquals(102, ticketWithId.getEventId(), "Event ID should be updated correctly");

        ticketWithId.setType("Standard");
        assertEquals("Standard", ticketWithId.getType(), "Type should be updated correctly");

        ticketWithId.setCount(200);
        assertEquals(200, ticketWithId.getCount(), "Count should be updated correctly");

        ticketWithId.setAvailable(150);
        assertEquals(150, ticketWithId.getAvailable(), "Available should be updated correctly");

        ticketWithId.setPrice(79.99);
        assertEquals(79.99, ticketWithId.getPrice(), "Price should be updated correctly");
    }

    @Test
    public void testToString() {
        String expectedString = "Ticket{id=1, eventId=101, type=VIP, price=99.99}";
        assertEquals(expectedString, ticketWithId.toString(), "toString should return the correct string representation");

        // Test updating the type and checking toString again
        ticketWithId.setType("Standard");
        expectedString = "Ticket{id=1, eventId=101, type=Standard, price=99.99}";
        assertEquals(expectedString, ticketWithId.toString(), "toString should return the updated string representation");
    }
}
