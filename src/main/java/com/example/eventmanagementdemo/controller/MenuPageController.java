package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.navigateTo;
import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;

public class MenuPageController {

    @FXML
    private Button upcomingEventsButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button messagesButton;

    @FXML
    private Label userName;

    private User user;

    public void initData(User user) {
      this.user = user;
      userName.setText("Hi " + user.getUsername() + " !");
        if (user.getRole().getId() == 1) {
            upcomingEventsButton.setVisible(false);
            upcomingEventsButton.setManaged(false);
        } else {
            eventsButton.setVisible(false);
            eventsButton.setManaged(false);
        }
    }


    @FXML
    private void handleUpcomingEvents() {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            CustomerViewController customerViewController = fxmlLoader.getController();
            customerViewController.initData(user);
            customerViewController.setPreviousScene(eventsButton.getScene());
        };
        navigateWithInitialData(eventsButton,"customer/customer-view.fxml", "customer/customer-view.css", loader);
    }

    @FXML
    private void handleEvents() {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            EventManagementController eventManagementController = fxmlLoader.getController();
            eventManagementController.initData(user);
            eventManagementController.setPreviousScene(eventsButton.getScene());
        };
        navigateWithInitialData(eventsButton,"event/event-page.fxml", "event/event-page.css", loader);
    }

    @FXML
    private void handleMessages() {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            MessagePageController messagePageController = fxmlLoader.getController();
            messagePageController.initData(user);
            messagePageController.setPreviousScreen(eventsButton.getScene());
        };
        navigateWithInitialData(eventsButton,"message/message-page.fxml", "message/message-page.css", loader);
    }

    public void handleLogout(ActionEvent actionEvent) {
        navigateTo(eventsButton,"login-page.fxml");
    }

    public void handleBookings(ActionEvent actionEvent) {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            BookingDetailsController bookingDetailsController = fxmlLoader.getController();
            bookingDetailsController.initData(user);
            bookingDetailsController.setPreviousScreen(eventsButton.getScene());
        };
        navigateWithInitialData(eventsButton,"showbooking/show-booking-page.fxml", "showbooking/show-booking-page.css", loader);

    }
}
