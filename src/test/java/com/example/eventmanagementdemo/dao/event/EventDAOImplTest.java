package com.example.eventmanagementdemo.dao.event;

import static org.junit.jupiter.api.Assertions.*;

import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;


public class EventDAOImplTest {

    private Connection connection;
    private EventDAOImpl eventDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = SqliteConnection.getInstance();
        Statement stmt = connection.createStatement();

        // Create the events table
        stmt.execute("CREATE TABLE events (id INT AUTO_INCREMENT PRIMARY KEY, eventName VARCHAR(255), hostedBy VARCHAR(255), date DATE, venue VARCHAR(255), phone VARCHAR(255))");

        // Initialize EventDAO
        eventDAO = new EventDAOImpl(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up the database
        connection.createStatement().execute("DROP TABLE events");
    }

    @Test
    public void testAddEvent() {
        // Create a new event
        Event event = new Event("Concert", "Music Group", LocalDate.of(2023, 12, 25), "Concert Hall", "1234567890");

        // Add the event
        eventDAO.addEvent(event);

        // Verify the event is added
        List<Event> events = eventDAO.getEvents();
        assertEquals(1, events.size(), "There should be 1 event");
        assertEquals("Concert", events.get(0).getEventName(), "Event name should be 'Concert'");
    }

//   @Test
    public void testGetEvent() {
        // Create and add a new event
        Event event = new Event("Concert", "Music Group", LocalDate.of(2023, 12, 25), "Concert Hall", "1234567890");
        eventDAO.addEvent(event);

        // Retrieve the event by its ID
        Event retrievedEvent = eventDAO.getEvent(event.getId());
        // Verify the event is retrieved correctly
        assertNotNull(retrievedEvent, "Event should be found");
        assertEquals("Concert", retrievedEvent.getEventName(), "Event name should be 'Concert'");
    }

    //@Test
    public void testUpdateEvent() {
        // Create and add a new event
        Event event = new Event("Concert", "Music Group", LocalDate.of(2023, 12, 25), "Concert Hall", "1234567890");
        eventDAO.addEvent(event);

        // Update the event
        event.setEventName("Updated Concert");
        eventDAO.updateEvent(event);

        // Verify the event is updated
        Event updatedEvent = eventDAO.getEvent(event.getId());
        assertEquals("Updated Concert", updatedEvent.getEventName(), "Event name should be 'Updated Concert'");
    }

    @Test
    public void testDeleteEvent() {
        // Create and add a new event
        Event event = new Event("Concert", "Music Group", LocalDate.of(2023, 12, 25), "Concert Hall", "1234567890");
        eventDAO.addEvent(event);

        // Delete the event
        eventDAO.deleteEvent(event);

        // Verify the event is deleted
        Event deletedEvent = eventDAO.getEvent(event.getId());
        assertNull(deletedEvent, "Event should be deleted");
    }

    @Test
    public void testGetEvents() {
        // Add multiple events
        Event event1 = new Event("Concert1", "Music Group1", LocalDate.of(2023, 12, 25), "Concert Hall1", "1234567891");
        Event event2 = new Event("Concert2", "Music Group2", LocalDate.of(2023, 12, 26), "Concert Hall2", "1234567892");
        eventDAO.addEvent(event1);
        eventDAO.addEvent(event2);

        // Retrieve all events
        List<Event> events = eventDAO.getEvents();

        // Verify all events are retrieved
        assertEquals(2, events.size(), "There should be 2 events");
        assertTrue(events.stream().anyMatch(event -> event.getEventName().equals("Concert1")), "Event 'Concert1' should be present");
        assertTrue(events.stream().anyMatch(event -> event.getEventName().equals("Concert2")), "Event 'Concert2' should be present");
    }
}
