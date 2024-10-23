package com.example.eventmanagementdemo.dao.ticket;

import com.example.eventmanagementdemo.model.Ticket;
import com.example.eventmanagementdemo.model.TicketType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements ITicketDAO {
    private final Connection connection;

    public TicketDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addTicket(Ticket ticket) {
        String query = "INSERT INTO tickets (eventId, type, count, available, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ticket.getEventId());
            preparedStatement.setString(2, ticket.getType());
            preparedStatement.setInt(3, ticket.getCount());
            preparedStatement.setInt(4, ticket.getCount());
            preparedStatement.setDouble(5, ticket.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getTicketsByEventId(int eventId) {
        String query = "SELECT * FROM tickets WHERE eventId = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                int count = resultSet.getInt("count");
                int available = resultSet.getInt("available");
                double price = resultSet.getDouble("price");
                tickets.add(new Ticket(id, eventId, type,count,available,price));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void updateTicket(Ticket ticket) {
        String query = "UPDATE tickets SET type = ?, count = ?, price = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ticket.getType());
            preparedStatement.setInt(2, ticket.getCount());
            preparedStatement.setDouble(3, ticket.getPrice());
            preparedStatement.setInt(4, ticket.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param id
     * @param available
     */
    @Override
    public void updateTicketAvailability(int id, int available) {
        String query = "UPDATE tickets SET available = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, available);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteTicket(int ticketId) {
        String query = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ticketId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteTicketsByEventId(int eventId){
        String query = "DELETE FROM tickets WHERE eventId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
