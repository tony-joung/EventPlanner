package com.example.eventmanagementdemo.dao.ticket;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Ticket;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class TicketDAOImplTest {

    private Connection connection;
    private TicketDAOImpl ticketDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.getInstance();

        // Initialize the DAO
        ticketDAO = new TicketDAOImpl(connection);

        // Create the necessary table for testing
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE tickets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "eventId INT, type TEXT, count INT, " +
                    "available INT, price REAL)");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the connection
        connection.createStatement().execute("DROP TABLE tickets");
    }

    @Test
    public void testAddTicket() {
        Ticket ticket = new Ticket(101, "VIP", 100, 100, 99.99);
        ticketDAO.addTicket(ticket);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals(1, tickets.size(), "Should have one ticket for the event");
    }

    @Test
    public void testGetTicketsByEventId() {
        Ticket ticket1 = new Ticket(101, "VIP", 100, 100, 99.99);
        Ticket ticket2 = new Ticket(101, "Standard", 200, 200, 49.99);
        ticketDAO.addTicket(ticket1);
        ticketDAO.addTicket(ticket2);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals(2, tickets.size(), "Should have two tickets for the event");
    }

    @Test
    public void testUpdateTicket() {
        Ticket ticket = new Ticket(101, "VIP", 100, 100, 99.99);
        ticketDAO.addTicket(ticket);

        Ticket updatedTicket = new Ticket(1, 101, "Premium", 150, 150, 119.99);
        ticketDAO.updateTicket(updatedTicket);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals("Premium", tickets.get(0).getType(), "Type should be updated");
        assertEquals(150, tickets.get(0).getCount(), "Count should be updated");
        assertEquals(119.99, tickets.get(0).getPrice(), "Price should be updated");
    }

    @Test
    public void testUpdateTicketAvailability() {
        Ticket ticket = new Ticket(101, "VIP", 100, 100, 99.99);
        ticketDAO.addTicket(ticket);

        ticketDAO.updateTicketAvailability(1, 80);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals(80, tickets.get(0).getAvailable(), "Available count should be updated");
    }

    @Test
    public void testDeleteTicket() {
        Ticket ticket = new Ticket(101, "VIP", 100, 100, 99.99);
        ticketDAO.addTicket(ticket);

        ticketDAO.deleteTicket(1);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals(0, tickets.size(), "Should have no tickets for the event after deletion");
    }

    @Test
    public void testDeleteTicketsByEventId() {
        Ticket ticket1 = new Ticket(101, "VIP", 100, 100, 99.99);
        Ticket ticket2 = new Ticket(101, "Standard", 200, 200, 49.99);
        Ticket ticket3 = new Ticket(102, "VIP", 50, 50, 149.99);
        ticketDAO.addTicket(ticket1);
        ticketDAO.addTicket(ticket2);
        ticketDAO.addTicket(ticket3);

        ticketDAO.deleteTicketsByEventId(101);

        List<Ticket> tickets = ticketDAO.getTicketsByEventId(101);
        assertEquals(0, tickets.size(), "Should have no tickets for the event 101 after deletion");

        List<Ticket> remainingTickets = ticketDAO.getTicketsByEventId(102);
        assertEquals(1, remainingTickets.size(), "Tickets for other events should remain");
    }
}
