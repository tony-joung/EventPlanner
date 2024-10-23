package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.dao.event.IEventDAO;
import com.example.eventmanagementdemo.dao.event.EventDAOImpl;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;
import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;


public class CustomerViewController {

    @FXML
    public Label noMessagesLabel;
    @FXML
    public VBox messageDetailPane;
    @FXML
    private ListView<Event> eventsListView;
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

    private final IEventDAO eventDAO;
    private User user;
    private Scene previousScene;

    private Event selectedEvent;

    public CustomerViewController() {
        Connection connection = SqliteConnection.getInstance();
        eventDAO = new EventDAOImpl(connection);
    }

    /**
     * Programmatically selects an event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void selectEvent(Event event) {
        this.selectedEvent = event;
        eventsListView.getSelectionModel().select(event);
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setValue(event.getDate());
        venueTextField.setText(event.getVenue());
        phoneTextField.setText(event.getPhone());
    }
    /**
     * Renders a cell in the event list view by setting the text to the event's full name.
     * @param eventListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Event> renderCell(ListView<Event> eventListView) {
        return new ListCell<>() {

            /**
             * Handles the event when an event is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onEventSelected(MouseEvent mouseEvent) {
                ListCell<Event> clickedCell = (ListCell<Event>) mouseEvent.getSource();
                // Get the selected contact from the list view
                Event selectedEvent = clickedCell.getItem();
                if (selectedEvent != null) {
                    noMessagesLabel.setVisible(false);
                    noMessagesLabel.setManaged(false);
                    messageDetailPane.setVisible(true);
                    selectEvent(selectedEvent);
                }
            }
            /**
             * Updates the item in the cell by setting the text to the event's full name.
             * @param event The event to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || event == null || event.getEventName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onEventSelected);
                } else {
                    setText(event.getEventName());
                }
            }
        };
    }

    @FXML
    public void initialize() {
        eventsListView.setCellFactory(this::renderCell);
        List<Event> events = eventDAO.getUpcomingEvents();
        if(events.isEmpty()) {
            noMessagesLabel.setVisible(true);
            noMessagesLabel.setManaged(true);
            return;
        }
        eventsListView.getItems().addAll(events);
    }


    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
           stage.close();
        }
    }

    public void initData(User user) {
        this.user = user;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void onMessageHostButtonClick(ActionEvent actionEvent) {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            ComposePageController composePageController = fxmlLoader.getController();
            composePageController.initData(user);
            composePageController.setPreviousScreen(nameTextField.getScene());
            composePageController.setRecipient(hostedByTextField.getText());
            composePageController.setSubject("Regarding your event: " + nameTextField.getText() + " - " + venueTextField.getText());
        };
        navigateWithInitialData(nameTextField, "compose/compose-page.fxml", "compose/compose-page.css", loader);
    }

    public void onBuyButtonClick(ActionEvent actionEvent) {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            BookingPageController bookingPageController = fxmlLoader.getController();
            bookingPageController.initData(user);
            bookingPageController.setEvent(this.selectedEvent);
            bookingPageController.setPreviousScreen(nameTextField.getScene());
        };
        navigateWithInitialData(nameTextField, "booking/booking-page.fxml", "booking/booking-page.css", loader);

    }
}
