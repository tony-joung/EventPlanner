package com.example.eventmanagementdemo.model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int eventId;
    private int ticketId;
    private int userId;
    private int quantity;
    private LocalDateTime bookingDate;

    public Booking(int id, int eventId, int ticketId, int userId, int quantity, LocalDateTime bookingDate) {
        this.id = id;
        this.eventId = eventId;
        this.ticketId = ticketId;
        this.userId = userId;
        this.quantity = quantity;
        this.bookingDate = bookingDate;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
