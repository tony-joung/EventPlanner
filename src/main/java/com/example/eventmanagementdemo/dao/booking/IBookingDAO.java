package com.example.eventmanagementdemo.dao.booking;

import com.example.eventmanagementdemo.model.Booking;
import com.example.eventmanagementdemo.model.BookingData;

import java.util.List;

public interface IBookingDAO {
    void insertBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBookingById(int id);
    List<BookingData> getBookingsByUserId(int id);
    void updateBooking(Booking booking);
    void deleteBooking(int id);
}
