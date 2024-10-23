package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.EventManagementApplication;
import com.example.eventmanagementdemo.dao.ticket.ITicketDAO;
import com.example.eventmanagementdemo.dao.ticket.TicketDAOImpl;
import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.dao.event.IEventDAO;
import com.example.eventmanagementdemo.dao.event.EventDAOImpl;
import com.example.eventmanagementdemo.model.Ticket;
import com.example.eventmanagementdemo.model.TicketType;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import com.example.eventmanagementdemo.utils.BackButtonListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.navigateTo;
import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;

public class EventDetailsController {

    @FXML
    public Button cancelButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField hostedByTextField;
    @FXML
    private DatePicker dateTextField;
    @FXML
    private TextField venueTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private VBox ticketOptionsVBox;

    private Event selectedEvent;

    private final IEventDAO eventDAO;
    private Scene previousScene;

    private ITicketDAO ticketDAO;

    private List<Ticket> existingTickets = new ArrayList<>();

    private BackButtonListener backButtonListener;

    public void setBackButtonListener(BackButtonListener listener) {
        this.backButtonListener = listener;
    }

    public EventDetailsController() {
        this.selectedEvent = null;
        Connection connection = SqliteConnection.getInstance();
        eventDAO = new EventDAOImpl(connection);
        ticketDAO = new TicketDAOImpl(connection);

    }


    public void initData(Event event) {
        this.selectedEvent = event;
        setEventDetails(event);
        loadExistingTickets(event.getId());
    }

    private void loadExistingTickets(int eventId) {
        existingTickets = ticketDAO.getTicketsByEventId(eventId);
            for (Ticket ticket : existingTickets) {
                addTicketType(ticket.getType(),ticket.getCount(), ticket.getPrice());
            }
    }


    /**
     * Programmatically selects a event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void setEventDetails(Event event) {
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setValue(event.getDate());
        venueTextField.setText(event.getVenue());
        phoneTextField.setText(event.getPhone());
    }

    /**
     * Handle the insert or update scenario of the event.
     * @param actionEvent
     */
    public void onnSaveConfirm(ActionEvent actionEvent) {
        Event newEvent;
        // save to the database based on the need.
        try {
            if (selectedEvent != null) {
                newEvent = onUpdate();
            } else {
                newEvent = new Event(nameTextField.getText(), hostedByTextField.getText(),
                        dateTextField.getValue(), venueTextField.getText(), phoneTextField.getText());
                eventDAO.addEvent(newEvent);
            }
            // Delete all existing tickets for this event
            ticketDAO.deleteTicketsByEventId(newEvent.getId());

            // Insert all current tickets
            for (Node node : ticketOptionsVBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox hbox = (HBox) node;
                    TextField ticketTypeTextField = (TextField) hbox.getChildren().get(0);
                    TextField ticketCountTextField = (TextField) hbox.getChildren().get(1);
                    TextField ticketPriceTextField = (TextField) hbox.getChildren().get(2);

                    String ticketType = ticketTypeTextField.getText();
                    int ticketCount = Integer.parseInt(ticketCountTextField.getText());
                    double ticketPrice = Double.parseDouble(ticketPriceTextField.getText());

                    Ticket newTicket = new Ticket(newEvent.getId(), ticketType, ticketCount,ticketCount, ticketPrice);
                    ticketDAO.addTicket(newTicket);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clearEvent();
        loadMainView();
    }


    /**
     * To update the DB with the edited event details.
     * @throws IOException throws the exception of the event update.
     */

    private Event onUpdate() throws IOException {
        // Get the selected contact from the list view
        Event updatedEvent = new Event(nameTextField.getText(), hostedByTextField.getText(),
                dateTextField.getValue(), venueTextField.getText(), phoneTextField.getText());
        updatedEvent.setId(this.selectedEvent.getId());
        eventDAO.updateEvent(updatedEvent);
        this.selectedEvent = null;
        return updatedEvent;
    }


    /*
     * updates the text fields with the "" information.
     */
    private void clearEvent() {
        nameTextField.setText("");
        hostedByTextField.setText("");
        dateTextField.setValue(LocalDate.now());
        venueTextField.setText("");
        phoneTextField.setText("");
    }

    /***
     * Load the previous list view.
     * @param actionEvent
     */
    @FXML
    public void onCancelClick(ActionEvent actionEvent) {
        loadMainView();
    }


    /**
     * To load the previous page with list view
     * refracted to be used in both add and confirm method.
     */
    private void loadMainView() {
        if (backButtonListener != null) {
            backButtonListener.onBackButtonPressed();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close(); // Example: close the current window
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        loadMainView();
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void addTicketType(String ticketType, int ticketCount, double ticketPrice) {
        HBox ticketTypeHBox = new HBox(10);

        // Text field for ticket type
        TextField ticketTypeTextField = new TextField(ticketType);
        ticketTypeTextField.setPromptText("Ticket Type");
        ticketTypeTextField.setPrefWidth(150.0);

        // Text field for ticket count
        TextField ticketCountTextField = new TextField(String.valueOf(ticketCount));
        ticketCountTextField.setPromptText("Count");
        ticketCountTextField.setPrefWidth(100.0);

        // Restrict input to numbers and decimal point
        ticketCountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ticketCountTextField.setText(oldValue);
            }
        });

        // Text field for ticket price
        TextField ticketPriceTextField = new TextField(String.valueOf(ticketPrice));
        ticketPriceTextField.setPromptText("Price");
        ticketPriceTextField.setPrefWidth(100.0);

        // Restrict input to numbers and decimal point
        ticketPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                ticketPriceTextField.setText(oldValue);
            }
        });

        // Button to delete this ticket type entry
        Button deleteButton = new Button("X");
        deleteButton.setOnAction(event -> ticketOptionsVBox.getChildren().remove(ticketTypeHBox));

        // Add all components to the HBox
        ticketTypeHBox.getChildren().addAll(ticketTypeTextField, ticketCountTextField, ticketPriceTextField, deleteButton);

        // Add the HBox to the VBox
        ticketOptionsVBox.getChildren().add(ticketTypeHBox);
    }

    // Overloaded method for adding a new empty ticket type entry
    public void addTicketType() {
        addTicketType("",0, 0.0);
    }

}
