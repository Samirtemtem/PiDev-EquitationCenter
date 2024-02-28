package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActivityDisplayController {

    @FXML
    private VBox activityBox;

    private ServiceActivity serviceActivity = new ServiceActivity();

    public void initialize() {
        try {
            List<Activity> activities = serviceActivity.ReadAll();
            for (Activity activity : activities) {
                HBox hbox = createActivityBox(activity);
                activityBox.getChildren().add(hbox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private HBox createActivityBox(Activity activity) {
        HBox hbox = new HBox(10);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        // Convert byte[] to JavaFX Image
        try {
            Image image = createImageFromBytes(activity.getImageData());
            imageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle image conversion error
        }

        Label titleLabel = new Label(activity.getTitle());
        titleLabel.getStyleClass().add("activity-title");

        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.getStyleClass().add("activity-description");
        Button plusInfosButton = new Button("Plus Infos");
        plusInfosButton.setOnAction(event -> {
            try {
                openActivityDetails(activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Add more labels or UI elements for other activity details if needed

        hbox.getChildren().addAll(imageView, titleLabel, descriptionLabel,plusInfosButton);
        return hbox;
    }

    private Image createImageFromBytes(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return new Image(bis);
    }

    public void close() {
        try {
            serviceActivity.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void openActivityDetails(Activity activity) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/ActivityDetails.fxml"));
        Parent root = loader.load();

        // Pass the Activity object to the ActivityDetailsController
        ActivityDetails controller = loader.getController();
        controller.initData(activity);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Activity Details");
        stage.show();
    }
}
