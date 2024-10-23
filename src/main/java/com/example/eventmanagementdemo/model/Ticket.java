package com.example.eventmanagementdemo.model;

public class Ticket {
    private int id;
    private int eventId;
    private String  type;
    private int count;
    private int available;
    private double price;

    // Constructor for creating a new ticket, usually without an ID
    public Ticket(int eventId, String type, int count,int available, double price) {
        this.eventId = eventId;
        this.type = type;
        this.count = count;
        this.available = available;
        this.price = price;
    }

    // Constructor for retrieving a ticket from the database with an ID
    public Ticket(int id, int eventId, String type,int count,int available, double price) {
        this.id = id;
        this.eventId = eventId;
        this.type = type;
        this.count = count;
        this.available = available;
        this.price = price;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for eventId
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }

    public void setAvailable(int available){
        this.available = available;
    }

    public int getAvailable(){
        return this.available;
    }


    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Override toString for better readability when printing a Ticket object
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}

