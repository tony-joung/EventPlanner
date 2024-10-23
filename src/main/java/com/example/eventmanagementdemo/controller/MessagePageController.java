package com.example.eventmanagementdemo.controller;

import com.example.eventmanagementdemo.dao.message.IMessageDAO;
import com.example.eventmanagementdemo.dao.message.MessageDAOImpl;
import com.example.eventmanagementdemo.model.Message;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.sqlite.SqliteConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;

public class MessagePageController {

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private Label fromLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox messageDetailPane;

    @FXML
    private Button composeMessageButton;

    private User user;

    private final IMessageDAO messageDAO;

    @FXML
    private Label noMessagesLabel;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Scene previousScene;

    public MessagePageController() {
        Connection connection = SqliteConnection.getInstance();
        this.messageDAO = new MessageDAOImpl(connection);
    }

    @FXML
    private void initialize() {
        System.out.println("Message Page Controller initialized");
        if(user == null) {
            return;
        }
        List<Message> messages = messageDAO.getMessagesByReceiver(user.getUsername());
        messageListView.getItems().addAll(messages);

        if (messages.isEmpty()) {
            noMessagesLabel.setVisible(true);
            clearMessageDetails();
            return;
        }
        messageDetailPane.setVisible(false);

        // Set a custom cell factory to display "regarding" and "date received"
        messageListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>() {
                    @Override
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getSubject() + " - " + item.getDatetime().format(formatter));
                        }
                    }
                };
            }
        });
    }

    private void clearMessageDetails() {
        fromLabel.setText("");
        messageLabel.setText("");
        dateLabel.setText("");
        subjectLabel.setText("");
    }

    @FXML
    private void handleMessageSelection() {
        Message selectedMessage = messageListView.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            messageDetailPane.setVisible(true);
            fromLabel.setText(selectedMessage.getSender());
            subjectLabel.setText(selectedMessage.getSubject());
            dateLabel.setText(selectedMessage.getDatetime().format(formatter).toString());
            messageLabel.setText(selectedMessage.getSubject());
        }
    }

    @FXML
    private void handleComposeMessage() {
        Consumer<FXMLLoader> loader = (fxmlLoader) -> {
            ComposePageController composePageController = fxmlLoader.getController();
            composePageController.initData(user);
            composePageController.setPreviousScreen(fromLabel.getScene());
        };
        navigateWithInitialData(fromLabel,"compose/compose-page.fxml", "compose/compose-page.css", loader);

    }

    @FXML
    private void handleBackAction() {
        Stage stage = (Stage) fromLabel.getScene().getWindow();
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            stage.close(); // Example: close the current window
        }
    }

    public void setPreviousScreen(Scene scene) {
        this.previousScene = scene;
    }

    public void initData(User user) {
        this.user = user;
        initialize();
    }

}
