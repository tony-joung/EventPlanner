package com.example.eventmanagementdemo.dao.message;

import com.example.eventmanagementdemo.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements IMessageDAO {

    private Connection connection;

    public MessageDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertMessage(Message message) {
        String query = "INSERT INTO messages (sender, receiver, subject, datetime, message) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, message.getSender());
            stmt.setString(2, message.getReceiver());
            stmt.setString(3, message.getSubject());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getDatetime()));
            stmt.setString(5, message.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages ORDER BY datetime DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("id"),
                        rs.getString("sender"),
                        rs.getString("receiver"),
                        rs.getString("subject"),
                        rs.getTimestamp("datetime").toLocalDateTime(),
                        rs.getString("message")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message getMessageById(int id) {
        String query = "SELECT * FROM messages WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Message(
                            rs.getInt("id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("subject"),
                            rs.getTimestamp("datetime").toLocalDateTime(),
                            rs.getString("message")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteMessage(int id) {
        String query = "DELETE FROM messages WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessagesByReceiver(String receiver) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE receiver = ? ORDER BY datetime DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, receiver);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message(
                            rs.getInt("id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("subject"),
                            rs.getTimestamp("datetime").toLocalDateTime(),
                            rs.getString("message")
                    );
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
