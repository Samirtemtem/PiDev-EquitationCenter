package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CarouselController implements Initializable {

    @FXML
    private HBox carouselBox;

    @FXML
    private ImageView imageView;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    private ServiceActivity serviceActivity;
    private List<Activity> activities;
    private int currentIndex = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceActivity = new ServiceActivity();

        try {
            activities = serviceActivity.ReadAll();
            displayCurrentActivity();

            configureButtons();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void configureButtons() {
        prevButton.setOnAction(event -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayCurrentActivity();
            }
        });

        nextButton.setOnAction(event -> {
            if (currentIndex < activities.size() - 1) {
                currentIndex++;
                displayCurrentActivity();
            }
        });

        updateButtonStatus();
    }

    private void displayCurrentActivity() {
        Activity currentActivity = activities.get(currentIndex);
        Image image = getImageFromByteArray(currentActivity.getImageData());
        imageView.setImage(image);

        updateButtonStatus();
    }

    private void updateButtonStatus() {
        prevButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex == activities.size() - 1);
    }

    private Image getImageFromByteArray(byte[] imageData) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
