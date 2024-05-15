package Controllers;

import Entities.Post;
import Service.ServicePost;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDate; // Import LocalDate for JavaFX DatePicker
import java.util.Date;

public class AddPostController {

    @FXML
    private DatePicker dateField;

    @FXML
    private TextArea descriptionField;

    private ServicePost servicePost = new ServicePost();

    @FXML
    private void addPost(ActionEvent event) {
        // Retrieve data from UI components
        LocalDate localDate = dateField.getValue(); // Get selected date from DatePicker
            Date date = java.sql.Date.valueOf(localDate); // Convert LocalDate to Date
        String description = descriptionField.getText();

        // Create a new Post object
        Post post = new Post(date, description);

        // Add the post to the database using the service
        try {
            servicePost.add(post);
            showSuccessMessage("Post added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            showErrorAlert("Error occurred while adding the post.");
        }
    }

    @FXML
    private ImageView btnReturn;

    @FXML
    private void returnTo(MouseEvent event) {
        RouterController router = new RouterController();
        router.navigate("/fxml/PostCrud.fxml");
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okayButton) {
                RouterController router = new RouterController();
                router.navigate("/fxml/PostCrud.fxml");
            }
        });
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void AddPost(ActionEvent actionEvent) {
        // This method is not needed and can be removed
    }
}
