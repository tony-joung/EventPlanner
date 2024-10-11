package com.example.eventmanagementdemo.model;

/**
 * A simple model class representing a event with basic details.
 */
public class Event {
    private int id;
    private String eventName;
    private String hostedBy;
    private String date;
    private String venue;
    private String phone;
    private String price;

    /**
     * Constructor for the event
     * @param eventName The name of the event
     * @param hostedBy The host of the event
     * @param date the date on which event will be held
     * @param venue the venue of the event
     * @param phone the contact number
     * @param
     */
    public Event(String eventName, String hostedBy, String date, String venue, String phone, String price) {
        // TODO: check the usage of ID as it has to be same as DB. (All args constructor)
        this.eventName = eventName;
        this.hostedBy = hostedBy;
        this.date = date;
        this.venue = venue;
        this.phone = phone;
        this.price = price;
    }

    /**
     *
     * @return the eventId
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get the eventname
     * @return the name of the event
     */
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHostedBy() {
        return hostedBy;
    }

    public void setHostedBy(String hostedBy) {
        this.hostedBy = hostedBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
