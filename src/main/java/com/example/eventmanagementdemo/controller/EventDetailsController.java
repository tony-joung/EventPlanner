package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.EventDAOImpl;
import com.example.eventmanagementdemo.model.IEventDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EventDetailsController {

    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;
    @FXML
    private ListView<Event> eventsListView;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField hostedByTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField venueTextField;
    @FXML
    private TextField phoneTextField;

    private final IEventDAO eventDAO;

    public EventDetailsController() {
        eventDAO = new EventDAOImpl();
    }

    /**
     * Programmatically selects a event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void setEventDetails(Event event) {
        eventsListView.getSelectionModel().select(event);
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setText(event.getDate());
        venueTextField.setText(event.getVenue());
        phoneTextField.setText(event.getPhone());
    }

    public void onnSaveConfirm(ActionEvent actionEvent) {
    }

    @FXML
    public void onCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
