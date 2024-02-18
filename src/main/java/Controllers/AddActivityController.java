package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.Date;

public class AddActivityController {

    @FXML
    private TextField Title;

    @FXML
    private TextField Type;

    @FXML
    private TextField Price;

    @FXML
    private DatePicker Date;

    @FXML
    private TextArea Description;

    private ServiceActivity serviceActivity = new ServiceActivity();

    @FXML
    public void AddActivity(ActionEvent actionEvent) {
        // Retrieve data from UI components
        String title = Title.getText();
        String type = Type.getText();
        double price = Double.parseDouble(Price.getText()); // Assuming Price is a double
        Date date = java.sql.Date.valueOf(Date.getValue()); // Convert DatePicker value to Date
        String description = Description.getText();

        // Create an Activity object
        Activity activity = new Activity();
        activity.setTitle(title);
        activity.setTypeActivity(type);
        activity.setPrice(price);
        activity.setDate(date);
        activity.setDescription(description);
        try {
            serviceActivity.add(activity);
            showSuccessMessage("Activité Ajoutée avec succées");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ImageView btnReturn;

    public void returnTo(MouseEvent mouseEvent) {
        RouterController router = new RouterController();
        router.navigate("/fxml/ActivitiesCRUD.fxml");
        System.out.println("Button Clicked");
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
                router.navigate("/fxml/ActivitiesCRUD.fxml");
                System.out.println("Button Clicked");
            }
        });
    }
}
