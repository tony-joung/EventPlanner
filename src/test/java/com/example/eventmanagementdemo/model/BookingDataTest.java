package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingDataTest {

    private BookingData bookingData;

    @BeforeEach
    public void setUp() {
        bookingData = new BookingData("Event Name", "2023-10-10", "Venue Name", 100, "VIP");
    }

    @Test
    public void testInitialValues() {
        assertEquals("Event Name", bookingData.getEventName(), "Event name should be initialized correctly");
        assertEquals("2023-10-10", bookingData.getEventDate(), "Event date should be initialized correctly");
        assertEquals("Venue Name", bookingData.getVenue(), "Venue should be initialized correctly");
        assertEquals(100, bookingData.getQuantity(), "Quantity should be initialized correctly");
        assertEquals("VIP", bookingData.getTicketType(), "Ticket type should be initialized correctly");
    }

    @Test
    public void testSettersAndGetters() {
        bookingData.setEventName("New Event Name");
        assertEquals("New Event Name", bookingData.getEventName(), "Event name should be updated correctly");

        bookingData.setEventDate("2023-12-12");
        assertEquals("2023-12-12", bookingData.getEventDate(), "Event date should be updated correctly");

        bookingData.setVenue("New Venue");
        assertEquals("New Venue", bookingData.getVenue(), "Venue should be updated correctly");

        bookingData.setQuantity(200);
        assertEquals(200, bookingData.getQuantity(), "Quantity should be updated correctly");

        bookingData.setTicketType("Standard");
        assertEquals("Standard", bookingData.getTicketType(), "Ticket type should be updated correctly");
    }

    @Test
    public void testPropertyMethods() {
        StringProperty eventNameProperty = bookingData.eventNameProperty();
        eventNameProperty.set("Updated Event Name");
        assertEquals("Updated Event Name", bookingData.getEventName(), "Event name property should reflect updated value");

        StringProperty eventDateProperty = bookingData.eventDateProperty();
        eventDateProperty.set("2023-11-11");
        assertEquals("2023-11-11", bookingData.getEventDate(), "Event date property should reflect updated value");

        StringProperty venueProperty = bookingData.venueProperty();
        venueProperty.set("Updated Venue");
        assertEquals("Updated Venue", bookingData.getVenue(), "Venue property should reflect updated value");

        IntegerProperty quantityProperty = bookingData.quantityProperty();
        quantityProperty.set(300);
        assertEquals(300, bookingData.getQuantity(), "Quantity property should reflect updated value");

        StringProperty ticketTypeProperty = bookingData.ticketTypeProperty();
        ticketTypeProperty.set("Premium");
        assertEquals("Premium", bookingData.getTicketType(), "Ticket type property should reflect updated value");
    }
}
