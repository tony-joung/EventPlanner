package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.model.Event;
import com.example.eventmanagementdemo.EventManagementApplication;
import com.example.eventmanagementdemo.dao.event.IEventDAO;
import com.example.eventmanagementdemo.dao.event.EventDAOImpl;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import com.example.eventmanagementdemo.utils.BackButtonListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.navigateTo;
import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;

public class EventManagementController implements BackButtonListener {
    @FXML
    public Button addButton;
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

    public EventManagementController() {
        Connection connection = SqliteConnection.getInstance();
        eventDAO = new EventDAOImpl(connection);
    }

    /**
     * Programmatically selects an event in the list view and
     * updates the text fields with the event's information.
     * @param event The event to select.
     */
    private void selectEvent(Event event) {
        eventsListView.getSelectionModel().select(event);
        nameTextField.setText(event.getEventName());
        hostedByTextField.setText(event.getHostedBy());
        dateTextField.setValue(event.getDate());
        venueTextField.setText(event.getVenue());
        phoneTextField.setText(event.getPhone());
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
     * Load the event-details.fxml with selected event.
     */
    @FXML
    private void onEditClick() throws IOException {
        // Get the selected contact from the list view
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            Stage stage = (Stage) addButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(EventManagementApplication.class.getResource("eventdetail/event-details.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), EventManagementApplication.WIDTH, EventManagementApplication.HEIGHT);
            String css = EventManagementApplication.class.getResource("eventdetail/event-details.css").toExternalForm();
            scene.getStylesheets().add(css);
            EventDetailsController eventDetailsController = fxmlLoader.getController();
            eventDetailsController.initData(selectedEvent);
            eventDetailsController.setPreviousScene(addButton.getScene());
            eventDetailsController.setBackButtonListener(this);
            stage.setScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("Please select a event to edit");
            alert.show();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected contact from the list view
        Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventDAO.deleteEvent(selectedEvent);
            clearEvent();
            syncEvents();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("Please select a event to edit");
            alert.show();
        }
    }

    /*@FXML
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
    }*/

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent)  {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            EventDetailsController eventDetailsController = fxmlLoader.getController();
            eventDetailsController.setPreviousScene(addButton.getScene());
            eventDetailsController.setBackButtonListener(this);
        };
        navigateWithInitialData(addButton, "eventdetail/event-details.fxml", "eventdetail/event-details.css",loader);
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) addButton.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            Consumer<FXMLLoader> loader = (fxmlLoader) -> {
                MenuPageController menuPageController = fxmlLoader.getController();
                menuPageController.initData(user);
            };
            navigateWithInitialData(addButton, "menu/menu-page.fxml", "menu/menu.css",loader);
        }
    }

    public void initData(User user) {
        this.user = user;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    @Override
    public void onBackButtonPressed() {
        syncEvents();
    }
}
