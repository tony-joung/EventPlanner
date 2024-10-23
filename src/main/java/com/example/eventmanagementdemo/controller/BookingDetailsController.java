package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.booking.BookingDAOImpl;
import com.example.eventmanagementdemo.dao.booking.IBookingDAO;
import com.example.eventmanagementdemo.dao.event.EventDAOImpl;
import com.example.eventmanagementdemo.dao.event.IEventDAO;
import com.example.eventmanagementdemo.model.*;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;

public class BookingDetailsController {

    @FXML
    private TableView<BookingData> bookingTableView;
    @FXML
    private TableColumn<BookingData, String> eventNameColumn;
    @FXML
    private TableColumn<BookingData, String> eventDateColumn;
    @FXML
    private TableColumn<BookingData, String> venueColumn;
    @FXML
    private TableColumn<BookingData, Integer> quantityColumn;
    @FXML
    private TableColumn<BookingData, String> ticketTypeColumn;

    private ObservableList<BookingData> bookingDataList;

    private IBookingDAO bookingDAO;

    private IEventDAO eventDAO;

    private User user;
    private Scene previousScene;

    public BookingDetailsController() {
        Connection connection = SqliteConnection.getInstance();
        this.bookingDAO = new BookingDAOImpl(connection);
        this.eventDAO = new EventDAOImpl(connection);
    }


    public void loadData() {
        // Initialize table columns
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ticketTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ticketType"));

        List<BookingData> bookingData = bookingDAO.getBookingsByUserId(user.getId());
        bookingDataList = FXCollections.observableArrayList(bookingData);
        bookingTableView.setItems(bookingDataList);
    }

    @FXML
    private void onBackToHome() {
        Stage stage = (Stage) bookingTableView.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close();
        }
    }

    public void initData(User user) {
        this.user = user;
        loadData();
    }

    public void setPreviousScreen(Scene scene) {
        this.previousScene = scene;
    }
}
