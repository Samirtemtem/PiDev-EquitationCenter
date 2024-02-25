package Controllers;

import Entities.Activity;
import Entities.ActivityType;
import Service.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class modifyActivityPopup implements Initializable {

    private int activityId;

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @FXML
    private TextField Title;
    @FXML
    private ComboBox<ActivityType> Type;

    @FXML
    private TextField Price;

    @FXML
    private DatePicker Date;

    @FXML
    private TextArea Description;

    private byte[] imageData;

    private ServiceActivity serviceActivity = new ServiceActivity();

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView btnReturn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.getItems().addAll(ActivityType.values());
        loadData();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to Activities CRUD");
                RouterController.navigate("/fxml/Activities/ActivitiesCRUD.fxml");
            }
        });
    }

    public void init(int activityId) {
        this.activityId = activityId;
        loadData();
    }

    private void loadData() {
        try {
            Activity activity = serviceActivity.findById(activityId);
            if (activity != null) {
                Title.setText(activity.getTitle());
                Type.setValue(ActivityType.valueOf(activity.getTypeActivity().name()));
                Description.setText(activity.getDescription());
                Price.setText(String.valueOf(activity.getPrice()));
                java.util.Date date = activity.getDate();
                Date.setValue(LocalDate.parse(activity.getDate().toString()));


                imageData = activity.getImageData();
                if (imageData != null && imageData.length > 0) {
                    imageView.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(activity.getImageData())));
                }
            } else {
                System.out.println("Activity not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    @FXML
    void ModifyActivity(ActionEvent event) {
        try {
            String title = Title.getText();
            ActivityType type = Type.getValue();
            String description = Description.getText();
            double price = Double.parseDouble(Price.getText());
            java.util.Date utilDate = java.sql.Date.valueOf(Date.getValue());

            // Update the activity
            Activity updatedActivity = new Activity(this.activityId, utilDate, type, title, description, price, imageData);
            serviceActivity.update(updatedActivity);
            showSuccessMessage("Votre activité a été modifiée avec succées");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void uploadImage(MouseEvent  event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionnez une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                imageData = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(imageData);
                fis.close();
                imageView.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(imageData)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
