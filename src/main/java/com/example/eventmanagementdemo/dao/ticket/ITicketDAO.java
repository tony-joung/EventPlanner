package com.example.eventmanagementdemo.dao.ticket;

import com.example.eventmanagementdemo.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface ITicketDAO {
    void addTicket(Ticket ticket);
    List<Ticket> getTicketsByEventId(int eventId) ;
    void updateTicket(Ticket ticket);
    void updateTicketAvailability(int id, int available);
    void deleteTicket(int ticketId);
    void deleteTicketsByEventId(int eventId);
}
