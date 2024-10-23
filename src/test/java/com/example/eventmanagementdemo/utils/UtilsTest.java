package com.example.eventmanagementdemo.utils;

import static com.example.eventmanagementdemo.utils.Utils.navigateWithInitialData;
import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class UtilsTest extends ApplicationTest {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Button button = new Button("Test Button");
        stage.setScene(new Scene(button, 300, 200));
        stage.show();
    }

    @Test
    public void testNavigateTo() {
        Button button = (Button) stage.getScene().getRoot();

        Platform.runLater(() -> {
            try {
                Utils.navigateTo(button, "testing.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
                fail("Exception during navigation: " + exception.getMessage());
            }
        });

        // Allow time for the navigation to take effect
        sleep(1000);

        Platform.runLater(() -> {
            assertNotNull(stage.getScene());
            assertNotEquals(button.getScene(), stage.getScene());
        });
    }

    @Test
    public void testShowError() {
        Platform.runLater(() -> {
            String title = "Error Title";
            String message = "Error message content";
            Utils.showError(title, message);
        });

        sleep(1000);
    }

    @Test
    public void testShowSuccess() {
        Platform.runLater(() -> {
            String title = "Success Title";
            String message = "Success message content";
            final boolean[] functionExecuted = {false};

            Runnable function = () -> functionExecuted[0] = true;

            Utils.showSuccess(title, message, function);

            assertFalse(functionExecuted[0]);
        });

        sleep(1000);
    }

    @Test
    public void testNavigateWithInitialData() throws Exception {
        Button button = (Button) stage.getScene().getRoot();

        WaitForAsyncUtils.waitForFxEvents();

        Platform.runLater(() -> {
            String page = "testing.fxml";
            String css = "main.css";
            Consumer<FXMLLoader> loader = fxmlLoader -> {};
            navigateWithInitialData(button, page, css, loader);
            Scene newScene = stage.getScene();
            assertNotNull(newScene, "New scene should not be null");
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}
