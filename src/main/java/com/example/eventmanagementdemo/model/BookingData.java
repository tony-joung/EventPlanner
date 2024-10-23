package com.example.eventmanagementdemo.model;

import javafx.beans.property.*;

public class BookingData {
    private final StringProperty eventName;
    private final StringProperty eventDate;
    private final StringProperty venue;
    private final IntegerProperty quantity;

    private final StringProperty ticketType;

    public BookingData(String eventName, String eventDate, String venue, int quantity, String ticketType) {
        this.eventName = new SimpleStringProperty(eventName);
        this.eventDate = new SimpleStringProperty(eventDate);
        this.venue = new SimpleStringProperty(venue);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.ticketType = new SimpleStringProperty(ticketType);
    }

    // Event Name
    public String getEventName() {
        return eventName.get();
    }

    public void setEventName(String eventName) {
        this.eventName.set(eventName);
    }

    public StringProperty eventNameProperty() {
        return eventName;
    }

    // Event Date
    public String getEventDate() {
        return eventDate.get();
    }

    public void setEventDate(String eventDate) {
        this.eventDate.set(eventDate);
    }

    public StringProperty eventDateProperty() {
        return eventDate;
    }

    // Venue
    public String getVenue() {
        return venue.get();
    }

    public void setVenue(String venue) {
        this.venue.set(venue);
    }

    public StringProperty venueProperty() {
        return venue;
    }

    // Quantity
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public String getTicketType() {
        return ticketType.get();
    }

    public void setTicketType(String ticketType) {
        this.ticketType.set(ticketType);
    }

    public StringProperty ticketTypeProperty() {
        return ticketType;
    }
}
