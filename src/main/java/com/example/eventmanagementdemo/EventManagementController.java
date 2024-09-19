package com.example.eventmanagementdemo;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EventManagementController {
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

    public EventManagementController() {
        eventDAO = new EventDAOImpl();
    }

    /**
     * Programmatically selects a event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void selectEvent(Event event) {
        eventsListView.getSelectionModel().select(event);
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setText(event.getDate());
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
                if (selectedEvent != null) selectEvent(selectedEvent);
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

    /**
     * Synchronizes the contacts list view with the contacts in the database.
     */
    private void syncEvents() {
        eventsListView.getItems().clear();
        eventsListView.getItems().addAll(eventDAO.getEvents());
    }

    @FXML
    public void initialize() {
        eventsListView.setCellFactory(this::renderCell);
        eventsListView.getItems().addAll(eventDAO.getEvents());
    }

    /**
     * onEditConfirm update the event after editing
     */
    @FXML
    private void onEditConfirm() {
        // Get the selected contact from the list view
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectedEvent.setEventName(nameTextField.getText());
            selectedEvent.setHostedBy(hostedByTextField.getText());
            selectedEvent.setDate(dateTextField.getText());
            selectedEvent.setVenue(venueTextField.getText());
            selectedEvent.setPhone(phoneTextField.getText());
            eventDAO.updateEvent(selectedEvent);
            syncEvents();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected contact from the list view
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventDAO.deleteEvent(selectedEvent.getId());
            syncEvents();
        }
    }

    @FXML
    private void onAdd() {
                Event newEvent = new Event(nameTextField.getText(), hostedByTextField.getText(),
                dateTextField.getText(), venueTextField.getText(), phoneTextField.getText());
        // Add the new contact to the database
        eventDAO.addEvent(newEvent);
        syncEvents();
        // Select the new contact in the list view
        // and focus the first name text field
        selectEvent(newEvent);
        nameTextField.requestFocus();
    }
}