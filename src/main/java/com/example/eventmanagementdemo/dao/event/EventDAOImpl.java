package com.example.eventmanagementdemo.dao.event;

import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The file to perform the DB operations.
 */

public class EventDAOImpl implements IEventDAO {

    private final Connection connection;

    public EventDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param event: The event to be added.
     */
    @Override
    public void addEvent(Event event) {
        String query = "INSERT INTO events (eventName, hostedBy, date, venue, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getEventName());
            statement.setString(2, event.getHostedBy());
            statement.setString(3, event.getDate().toString());
            statement.setString(4, event.getVenue());
            statement.setString(5, event.getPhone());
            statement.executeUpdate();
            // Set the id of the new event
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Get the list of events available.
     * @return all the events available in the table.
     */
    @Override
    public List<Event> getEvents() {
        List<Event> eventList = new ArrayList<>();
        try {
            Statement getStatement = connection.createStatement();
            String query = "SELECT * FROM events ORDER BY date DESC";
            ResultSet resultSet = getStatement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String eventName = resultSet.getString("eventName");
                String hostedBy = resultSet.getString("hostedBy");
                Date date = Date.valueOf( resultSet.getString("date"));
                String venue = resultSet.getString("venue");
                String phone = resultSet.getString("phone");
                Event event = new Event(eventName, hostedBy, date.toLocalDate(), venue, phone);
                event.setId(id);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }

    /**
     * get the event based on the ID
     * @param id : Id of the event to be retrieved
     * @return event : the details of the selected event.
     */
    @Override
    public Event getEvent(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String eventName = resultSet.getString("eventName");
                String hostedBy = resultSet.getString("hostedBy");
                Date date = Date.valueOf(resultSet.getString("date"));
                String venue = resultSet.getString("venue");
                String phone = resultSet.getString("phone");
                Event event = new Event(eventName, hostedBy, date.toLocalDate(), venue, phone);
                event.setId(id);
                return event;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param event : event to be deleted.
     */
    @Override
    public void deleteEvent(Event event) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE id = ?");
            statement.setInt(1, event.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param event Event to be updated.
     */
    @Override
    public void updateEvent(Event event) {
        String query = "UPDATE events SET eventName = ?, hostedBy = ?, date = ?, venue = ?, phone = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventName());
            statement.setString(2, event.getHostedBy());
            statement.setString(3, event.getDate().toString());
            statement.setString(4, event.getVenue());
            statement.setString(5, event.getPhone());
            statement.setString(6, String.valueOf(event.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getUpcomingEvents() {
        List<Event> eventList = new ArrayList<>();
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // SQL query to select upcoming events
            String query = "SELECT * FROM events WHERE date >= ? ORDER BY date ASC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentDate.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String eventName = resultSet.getString("eventName");
                String hostedBy = resultSet.getString("hostedBy");
                Date date = Date.valueOf( resultSet.getString("date"));
                String venue = resultSet.getString("venue");
                String phone = resultSet.getString("phone");
                Event event = new Event(eventName, hostedBy, date.toLocalDate(), venue, phone);
                event.setId(id);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eventList;
    }
}