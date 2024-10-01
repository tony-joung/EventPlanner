package com.example.eventmanagementdemo.model;

public class Event {
    private int id;
    private String eventName;
    private String hostedBy;
    private String date;
    private String venue;
    private String phone;

    public Event(String eventName, String hostedBy, String date, String venue, String phone) {
        // TODO: check the usage of ID as it has to be same as DB. (All args constructor)
        this.eventName = eventName;
        this.hostedBy = hostedBy;
        this.date = date;
        this.venue = venue;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
