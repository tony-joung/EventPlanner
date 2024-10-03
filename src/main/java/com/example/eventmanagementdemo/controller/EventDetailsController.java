package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.EventManagementApplication;
import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.model.IEventDAO;
import com.example.eventmanagementdemo.model.SqliteEventDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EventDetailsController {

    @FXML
    public Button cancelButton;
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

    private Event selectedEvent;

    private final IEventDAO eventDAO;

    public EventDetailsController() {
        this.selectedEvent = null;

        eventDAO = new SqliteEventDao();

    }


    public void initData(Event event) {
        this.selectedEvent = event;
        setEventDetails(event);
    }


    /**
     * Programmatically selects a event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void setEventDetails(Event event) {
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setText(event.getDate());
        venueTextField.setText(event.getVenue());
        phoneTextField.setText(event.getPhone());
    }

    /**
     * Handle the insert or update scenario of the event.
     * @param actionEvent
     */
    public void onnSaveConfirm(ActionEvent actionEvent) {
        Event newEvent = new Event(nameTextField.getText(), hostedByTextField.getText(),
                dateTextField.getText(), venueTextField.getText(), phoneTextField.getText());
        // save to the database based on the need.
        try {
            if (selectedEvent != null) {
                onUpdate();
            } else {
                eventDAO.addEvent(newEvent);
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

    private void onUpdate() throws IOException {
        // Get the selected contact from the list view
        Event updatedEvent = new Event(nameTextField.getText(), hostedByTextField.getText(),
                dateTextField.getText(), venueTextField.getText(), phoneTextField.getText());
        updatedEvent.setId(this.selectedEvent.getId());
        eventDAO.updateEvent(updatedEvent);
        this.selectedEvent = null;
    }


    /*
     * updates the text fields with the "" information.
     */
    private void clearEvent() {
        nameTextField.setText("");
        hostedByTextField.setText("");
        dateTextField.setText("");
        venueTextField.setText("");
        phoneTextField.setText("");
    }

    /***
     * Load the previous list view.
     * @param actionEvent
     */
    @FXML
    public void onCancelClick(ActionEvent actionEvent) {
        /*Stage stage = (Stage) cancelButton.getScene().getWindow().;
        stage.close();*/
        loadMainView();
    }


    /**
     * To load the previous page with list view
     * refracted to be used in both add and confirm method.
     */
    private void loadMainView() {

        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(EventManagementApplication.class.getResource("hello-view.fxml"));
            Scene scene;
            scene = new Scene(fxmlLoader.load(), EventManagementApplication.WIDTH, EventManagementApplication.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
