package com.example.eventmanagementdemo.dao.message;

import com.example.eventmanagementdemo.model.Message;

import java.util.List;

public interface IMessageDAO {
    void insertMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(int id);
    void deleteMessage(int id);
    List<Message> getMessagesByReceiver(String receiver);
}
