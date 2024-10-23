package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.message.IMessageDAO;
import com.example.eventmanagementdemo.dao.message.MessageDAOImpl;
import com.example.eventmanagementdemo.model.Message;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.time.LocalDateTime;

public class ComposePageController {

    @FXML
    private TextField toField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea messageArea;

    private User user;

    private final IMessageDAO messageDAO;
    private Scene previousScene;


    public ComposePageController() {
        Connection connection = SqliteConnection.getInstance();
        this.messageDAO = new MessageDAOImpl(connection);
    }

    @FXML
    private void handleSendMessage() {
        String receiver = toField.getText().trim();
        String subject = subjectField.getText().trim();
        String messageContent = messageArea.getText().trim();

        if (receiver.isEmpty() || messageContent.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Recipient and message content cannot be empty.");
            alert.showAndWait();
            return;
        }

        Message message = new Message(user.getUsername(), receiver, subject, LocalDateTime.now(), messageContent);
        messageDAO.insertMessage(message);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message Sent");
        alert.setHeaderText(null);
        alert.setContentText("Your message has been sent successfully.");
        alert.showAndWait();

        // Clear the fields after sending
        toField.clear();
        subjectField.clear();
        messageArea.clear();
        handleBackAction();
    }

    @FXML
    private void handleBackAction() {
        Stage stage = (Stage) toField.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close(); // Example: close the current window
        }
    }

    public void initData(User user) {
        this.user = user;
    }

    public void setPreviousScreen(Scene scene) {
        this.previousScene = scene;
    }

    public void setRecipient(String text) {
        this.toField.setText(text);
        this.toField.setDisable(true);
    }

    public void setSubject(String subject) {
        this.subjectField.setText(subject);
        this.subjectField.setDisable(true);
    }
}
