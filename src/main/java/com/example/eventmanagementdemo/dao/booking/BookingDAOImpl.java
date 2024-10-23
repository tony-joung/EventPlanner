package com.example.eventmanagementdemo.dao.booking;

import com.example.eventmanagementdemo.model.Booking;
import com.example.eventmanagementdemo.model.BookingData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements IBookingDAO {
    private final Connection connection;

    public BookingDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertBooking(Booking booking) {
        String query = "INSERT INTO bookings (eventId, ticketId, userId, quantity, bookingDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getEventId());
            preparedStatement.setInt(2, booking.getTicketId());
            preparedStatement.setInt(3, booking.getUserId());
            preparedStatement.setInt(4, booking.getQuantity());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Booking booking = new Booking(
                        resultSet.getInt("id"),
                        resultSet.getInt("eventId"),
                        resultSet.getInt("ticketId"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("bookingDate").toLocalDateTime()
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public Booking getBookingById(int id) {
        String query = "SELECT * FROM bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Booking(
                            resultSet.getInt("id"),
                            resultSet.getInt("eventId"),
                            resultSet.getInt("ticketId"),
                            resultSet.getInt("userId"),
                            resultSet.getInt("quantity"),
                            resultSet.getTimestamp("bookingDate").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookingData> getBookingsByUserId(int userId) {
        List<BookingData> bookings = new ArrayList<>();
        String query = "SELECT b.id, b.eventId, b.ticketId, b.userId, b.quantity, b.bookingDate, " +
                "e.eventname AS eventName, e.date AS eventDate, e.venue, " +
                "t.type AS ticketType " +
                "FROM bookings b " +
                "JOIN events e ON b.eventId = e.id " +
                "JOIN tickets t ON b.ticketId = t.id " +
                "WHERE b.userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BookingData bookingData = new BookingData(
                            resultSet.getString("eventName"),
                            resultSet.getString("eventDate"),
                            resultSet.getString("venue"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("ticketType") // Include the ticket type
                    );
                    bookings.add(bookingData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void updateBooking(Booking booking) {
        String query = "UPDATE bookings SET eventId = ?, ticketId = ?, userId = ?, quantity = ?, bookingDate = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getEventId());
            preparedStatement.setInt(2, booking.getTicketId());
            preparedStatement.setInt(3, booking.getUserId());
            preparedStatement.setInt(4, booking.getQuantity());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            preparedStatement.setInt(6, booking.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooking(int id) {
        String query = "DELETE FROM bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
