package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    private Booking booking;
    private LocalDateTime bookingDate;

    @BeforeEach
    public void setUp() {
        bookingDate = LocalDateTime.of(2023, 10, 10, 15, 30);
        booking = new Booking(1, 101, 201, 301, 5, bookingDate);
    }

    @Test
    public void testConstructorInitializesFields() {
        assertEquals(1, booking.getId(), "ID should be initialized to 1");
        assertEquals(101, booking.getEventId(), "Event ID should be initialized to 101");
        assertEquals(201, booking.getTicketId(), "Ticket ID should be initialized to 201");
        assertEquals(301, booking.getUserId(), "User ID should be initialized to 301");
        assertEquals(5, booking.getQuantity(), "Quantity should be initialized to 5");
        assertEquals(bookingDate, booking.getBookingDate(), "Booking date should be initialized to the specified date");
    }

    @Test
    public void testSettersAndGetters() {
        booking.setId(2);
        assertEquals(2, booking.getId(), "ID should be updated to 2");

        booking.setEventId(102);
        assertEquals(102, booking.getEventId(), "Event ID should be updated to 102");

        booking.setTicketId(202);
        assertEquals(202, booking.getTicketId(), "Ticket ID should be updated to 202");

        booking.setUserId(302);
        assertEquals(302, booking.getUserId(), "User ID should be updated to 302");

        booking.setQuantity(10);
        assertEquals(10, booking.getQuantity(), "Quantity should be updated to 10");

        LocalDateTime newBookingDate = LocalDateTime.of(2023, 11, 11, 16, 45);
        booking.setBookingDate(newBookingDate);
        assertEquals(newBookingDate, booking.getBookingDate(), "Booking date should be updated to the new date");
    }

    @Test
    public void testToString() {
        String expectedString = "Booking{id=1, eventId=101, ticketId=201, userId=301, quantity=5, bookingDate=" + bookingDate + "}";
        assertEquals(expectedString, booking.toString(), "toString should return the correct string representation");
    }
}
