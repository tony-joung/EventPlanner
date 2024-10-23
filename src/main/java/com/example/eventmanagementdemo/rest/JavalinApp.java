package com.example.eventmanagementdemo.rest;

import io.javalin.Javalin;
import com.example.eventmanagementdemo.model.Booking;
import com.example.eventmanagementdemo.dao.booking.IBookingDAO;
import com.example.eventmanagementdemo.dao.booking.BookingDAOImpl;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;

import java.sql.Connection;
import java.util.List;

public class JavalinApp {

    public static void main(String[] args) {
        Connection connection = SqliteConnection.getInstance();
        IBookingDAO bookingDAO = new BookingDAOImpl(connection);

        Javalin app = Javalin.create().start(8080);

        app.get("/bookings", ctx -> {
            List<Booking> bookings = bookingDAO.getAllBookings();
            ctx.json(bookings);
        });

        app.get("/bookings/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Booking booking = bookingDAO.getBookingById(id);
            if (booking != null) {
                ctx.json(booking);
            } else {
                ctx.status(404).result("Booking not found");
            }
        });

        app.post("/bookings", ctx -> {
            Booking booking = ctx.bodyAsClass(Booking.class);
            bookingDAO.insertBooking(booking);
            ctx.status(201).json(booking);
        });

        app.put("/bookings/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Booking booking = ctx.bodyAsClass(Booking.class);
            booking.setId(id);
            bookingDAO.updateBooking(booking);
            ctx.status(204);
        });

        app.delete("/bookings/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            bookingDAO.deleteBooking(id);
            ctx.status(204);
        });
    }
}