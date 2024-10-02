package com.example.eventmanagementdemo.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The file to perform the DB operations.
 */

public class SqliteEventDao implements IEventDAO{

    private final Connection connection;

    public SqliteEventDao() {
        connection = SqliteConnection.getInstance();
        createTable();
        //insertSampleData();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS events ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "eventName VARCHAR NOT NULL,"
                    + "hostedBy VARCHAR NOT NULL,"
                    + "date VARCHAR NOT NULL,"
                    + "venue VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void insertSampleData() {
        try {
            // Clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM events";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO events (eventName, hostedBy, date, venue, phone) VALUES "
                    + "('Event1', 'John', '10/09/2024', 'Hall 1', '1234567'),"
                    + "('Event2', 'tesst', '11/09/2024', 'Hall 2', '0987655'),"
                    + "('Event3', 'test', '12/09/2024', 'Hall 3', '34567889')";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    /**
     * @param event: The event to be added.
     */
    @Override
    public void addEvent(Event event) {
        String query = "INSERT INTO events (eventName, hostedBy, date, venue, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventName());
            statement.setString(2, event.getHostedBy());
            statement.setString(3, event.getDate());
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
            String query = "SELECT * FROM events";
            ResultSet resultSet = getStatement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String eventName = resultSet.getString("eventName");
                String hostedBy = resultSet.getString("hostedBy");
                String date = resultSet.getString("date");
                String venue = resultSet.getString("venue");
                String phone = resultSet.getString("phone");
                Event event = new Event(eventName, hostedBy, date, venue, phone);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String eventName = resultSet.getString("eventName");
                String hostedBy = resultSet.getString("hostedBy");
                String date = resultSet.getString("date");
                String venue = resultSet.getString("venue");
                String phone = resultSet.getString("phone");
                Event event = new Event(eventName, hostedBy, date, venue, phone);
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
            statement.setString(3, event.getDate());
            statement.setString(4, event.getVenue());
            statement.setString(5, event.getPhone());
            statement.setString(6, String.valueOf(event.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}