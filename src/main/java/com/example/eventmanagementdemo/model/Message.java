package com.example.eventmanagementdemo.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String sender;
    private String receiver;
    private String subject;
    private LocalDateTime datetime;
    private String message;

    // Constructor for creating new messages (without ID)
    public Message(String sender, String receiver, String subject, LocalDateTime datetime, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.datetime = datetime;
        this.message = message;
    }

    // Constructor for fetching messages with an ID
    public Message(int id, String sender, String receiver, String subject, LocalDateTime datetime, String message) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.datetime = datetime;
        this.message = message;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
