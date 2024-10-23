package com.example.eventmanagementdemo.model;

import javafx.beans.property.*;

public class TicketData {
    private final IntegerProperty id;
    private final StringProperty type;
    private final DoubleProperty price;
    private final IntegerProperty available;
    private final IntegerProperty quantity;

    public TicketData(int id, String type, double price, int available, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleDoubleProperty(price);
        this.available = new SimpleIntegerProperty(available);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getAvailable() {
        return available.get();
    }

    public void setAvailable(int available) {
        this.available.set(available);
    }

    public IntegerProperty availableProperty() {
        return available;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return this.id.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
