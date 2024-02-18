package Controllers;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RouterController {

    private static Stage primaryStage;
    private static final Duration TRANSITION_DURATION = Duration.seconds(1.0);


    // Set the primary stage for the application
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Load and display an FXML file in the primary stage
    public static void navigate(String fxmlPath) {
            try {
                FXMLLoader loader = new FXMLLoader(RouterController.class.getResource(fxmlPath));
                AnchorPane root = loader.load();

                // Fade out animation
                FadeTransition fadeOut = new FadeTransition(TRANSITION_DURATION, primaryStage.getScene().getRoot());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> {
                    primaryStage.setScene(new Scene(root));
                    // Fade in animation
                    FadeTransition fadeIn = new FadeTransition(TRANSITION_DURATION, root);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });
                fadeOut.play();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static void navigate(String fxmlPath, Integer activityId) {
        try {
            FXMLLoader loader = new FXMLLoader(RouterController.class.getResource(fxmlPath));
            AnchorPane root = loader.load();

            // Access the controller
            modifyActivityPopup controller = loader.getController();

            // If activityId is not null, initialize the controller with the activity ID
            if (activityId != null) {
                controller.init(activityId);
            }


            FadeTransition fadeOut = new FadeTransition(TRANSITION_DURATION, primaryStage.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                primaryStage.setScene(new Scene(root));
                // Fade in animation
                FadeTransition fadeIn = new FadeTransition(TRANSITION_DURATION, root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
