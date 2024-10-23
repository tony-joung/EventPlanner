package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.booking.BookingDAOImpl;
import com.example.eventmanagementdemo.dao.booking.IBookingDAO;
import com.example.eventmanagementdemo.dao.event.EventDAOImpl;
import com.example.eventmanagementdemo.dao.event.IEventDAO;
import com.example.eventmanagementdemo.dao.ticket.ITicketDAO;
import com.example.eventmanagementdemo.dao.ticket.TicketDAOImpl;
import com.example.eventmanagementdemo.model.*;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.util.List;
import javafx.util.Callback;

public class BookingPageController {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TableView<TicketData> ticketTableView;
    @FXML
    private TableColumn<TicketData, String> ticketTypeColumn;
    @FXML
    private TableColumn<TicketData, Double> priceColumn;
    @FXML
    private TableColumn<TicketData, Integer> availableColumn;
    @FXML
    private TableColumn<TicketData, Integer> quantityColumn;

    private Event event;
    private ObservableList<TicketData> ticketDataList;

    private IEventDAO eventDAO;

    private ITicketDAO ticketDAO;

    private IBookingDAO bookingDAO;

    private User user;
    private Scene previousScene;

    public BookingPageController() {
        Connection connection = SqliteConnection.getInstance();
        this.eventDAO = new EventDAOImpl(connection);
        this.ticketDAO = new TicketDAOImpl(connection);
        this.bookingDAO = new BookingDAOImpl(connection);
    }

    public void initialize() {
        ticketTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Set the table to be editable
        ticketTableView.setEditable(true);

        // Enable editing on the quantity column with custom styling
        quantityColumn.setCellFactory(column -> {
            TextFieldTableCell<TicketData, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter()) {
                @Override
                public void startEdit() {
                    super.startEdit();

                    // Access the TextField editor
                    TextField textField = (TextField) getGraphic();
                    if (textField != null) {
                        // Add a listener to commit the edit when focus is lost
                        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                if (!newValue) { // Focus lost
                                    try {
                                        // Parse and commit the new value
                                        Integer newValue2 = Integer.parseInt(textField.getText());
                                        commitEdit(newValue2);
                                    } catch (NumberFormatException e) {
                                        // Handle invalid input gracefully
                                        cancelEdit();
                                    }
                                }
                            }
                        });
                    }
                }
            };
            cell.getStyleClass().add("quantity-cell");
            return cell;
        });

        // Handle edit commit
        quantityColumn.setOnEditCommit(event -> {
            TicketData ticketData = event.getRowValue();
            Integer newValue = event.getNewValue();
            if (newValue != null && newValue <= ticketData.getAvailable()) {
                ticketData.setQuantity(newValue);
                System.out.println("Quantity updated to: " + newValue);
            } else {
                System.out.println("Invalid input: Quantity exceeds available tickets. Keeping old value.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Quantity");
                alert.setHeaderText("Quantity Exceeds Available Tickets");
                alert.setContentText("Please enter a quantity less than or equal to the available tickets.");
                alert.showAndWait();
                ticketTableView.refresh(); // Refresh to revert the incorrect value
            }
        });

        ticketDataList = FXCollections.observableArrayList();
        ticketTableView.setItems(ticketDataList);
    }

    public void setEvent(Event event) {
        this.event = event;
        eventNameLabel.setText(event.getEventName());
        venueLabel.setText(event.getVenue());
        dateLabel.setText(event.getDate().toString());

        loadTickets();
    }

    private void loadTickets() {
        // Load tickets from the database for the given event
        ticketDataList.clear();
        List<Ticket> tickets = ticketDAO.getTicketsByEventId(event.getId());

        for (Ticket ticket : tickets) {
            ticketDataList.add(new TicketData(ticket.getId(),ticket.getType(), ticket.getPrice(), ticket.getAvailable(), 0));
        }
    }

    @FXML
    private void onBookTickets(ActionEvent event) {
        // Loop through the ticketDataList and process the booking
        for (TicketData ticketData : ticketDataList) {
            if (ticketData.getQuantity() > 0) {
                // Update the available count in the database
                ticketDAO.updateTicketAvailability(ticketData.getId(), ticketData.getAvailable() - ticketData.getQuantity());

                // Insert booked tickets into the booking table
                Booking booking = new Booking(
                        0,
                        this.event.getId(),
                        ticketData.getId(),
                        user.getId(),
                        ticketData.getQuantity(),
                        java.time.LocalDateTime.now()
                );
                bookingDAO.insertBooking(booking);
            }
        }

        // Optionally, show a confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tickets successfully booked!");
        alert.showAndWait();

        // Refresh the available tickets
        loadTickets();
    }

    public void initData(User user) {
        this.user = user;
    }

    public void setPreviousScreen(Scene scene) {
        this.previousScene = scene;
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) eventNameLabel.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close();
        }
    }
}
