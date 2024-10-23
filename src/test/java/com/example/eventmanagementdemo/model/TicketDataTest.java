package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketDataTest {

    private TicketData ticketData;

    @BeforeEach
    public void setUp() {
        ticketData = new TicketData(1, "VIP", 99.99, 50, 10);
    }

    @Test
    public void testInitialValues() {
        assertEquals(1, ticketData.getId(), "ID should be initialized correctly");
        assertEquals("VIP", ticketData.getType(), "Type should be initialized correctly");
        assertEquals(99.99, ticketData.getPrice(), "Price should be initialized correctly");
        assertEquals(50, ticketData.getAvailable(), "Available should be initialized correctly");
        assertEquals(10, ticketData.getQuantity(), "Quantity should be initialized correctly");
    }

    @Test
    public void testSettersAndGetters() {
        ticketData.setId(2);
        assertEquals(2, ticketData.getId(), "ID should be updated correctly");

        ticketData.setType("Standard");
        assertEquals("Standard", ticketData.getType(), "Type should be updated correctly");

        ticketData.setPrice(79.99);
        assertEquals(79.99, ticketData.getPrice(), "Price should be updated correctly");

        ticketData.setAvailable(30);
        assertEquals(30, ticketData.getAvailable(), "Available should be updated correctly");

        ticketData.setQuantity(5);
        assertEquals(5, ticketData.getQuantity(), "Quantity should be updated correctly");
    }

    @Test
    public void testPropertyMethods() {
        IntegerProperty idProperty = ticketData.idProperty();
        idProperty.set(3);
        assertEquals(3, ticketData.getId(), "ID property should reflect updated value");

        StringProperty typeProperty = ticketData.typeProperty();
        typeProperty.set("Economy");
        assertEquals("Economy", ticketData.getType(), "Type property should reflect updated value");

        DoubleProperty priceProperty = ticketData.priceProperty();
        priceProperty.set(59.99);
        assertEquals(59.99, ticketData.getPrice(), "Price property should reflect updated value");

        IntegerProperty availableProperty = ticketData.availableProperty();
        availableProperty.set(40);
        assertEquals(40, ticketData.getAvailable(), "Available property should reflect updated value");

        IntegerProperty quantityProperty = ticketData.quantityProperty();
        quantityProperty.set(15);
        assertEquals(15, ticketData.getQuantity(), "Quantity property should reflect updated value");
    }
}
