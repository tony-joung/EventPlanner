package com.example.eventmanagementdemo.dao.booking;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Booking;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDAOImplTest {

    private Connection connection;
    private BookingDAOImpl bookingDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.getInstance();
        Statement stmt = connection.createStatement();

        // Initialize the DAO
        bookingDAO = new BookingDAOImpl(connection);

        // Create the necessary table for testing
        stmt.execute("CREATE TABLE bookings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventId INT, ticketId INT, userId INT, " +
                "quantity INT, bookingDate DATETIME)");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Drop the table after each test
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE bookings");
        }
    }

    @Test
    public void testInsertBooking() {
        Booking booking = new Booking(0, 101, 201, 301, 5, LocalDateTime.now());
        bookingDAO.insertBooking(booking);

        List<Booking> bookings = bookingDAO.getAllBookings();
        assertEquals(1, bookings.size(), "Should have one booking in the database");
    }

    @Test
    public void testGetAllBookings() {
        Booking booking1 = new Booking(0, 101, 201, 301, 5, LocalDateTime.now());
        Booking booking2 = new Booking(0, 102, 202, 302, 10, LocalDateTime.now());
        bookingDAO.insertBooking(booking1);
        bookingDAO.insertBooking(booking2);

        List<Booking> bookings = bookingDAO.getAllBookings();
        assertEquals(2, bookings.size(), "Should have two bookings in the database");
    }

    @Test
    public void testGetBookingById() {
        Booking booking = new Booking(0, 101, 201, 301, 5, LocalDateTime.now());
        bookingDAO.insertBooking(booking);

        Booking retrievedBooking = bookingDAO.getBookingById(1);
        assertNotNull(retrievedBooking, "Booking should not be null");
        assertEquals(101, retrievedBooking.getEventId(), "Event ID should match");
    }

    @Test
    public void testUpdateBooking() {
        Booking booking = new Booking(0, 101, 201, 301, 5, LocalDateTime.now());
        bookingDAO.insertBooking(booking);

        Booking updatedBooking = new Booking(1, 102, 202, 302, 10, LocalDateTime.now().plusDays(1));
        bookingDAO.updateBooking(updatedBooking);

        Booking retrievedBooking = bookingDAO.getBookingById(1);
        assertEquals(102, retrievedBooking.getEventId(), "Event ID should be updated");
    }

    @Test
    public void testDeleteBooking() {
        Booking booking = new Booking(0, 101, 201, 301, 5, LocalDateTime.now());
        bookingDAO.insertBooking(booking);

        bookingDAO.deleteBooking(1);

        Booking retrievedBooking = bookingDAO.getBookingById(1);
        assertNull(retrievedBooking, "Booking should be null after deletion");
    }

}
