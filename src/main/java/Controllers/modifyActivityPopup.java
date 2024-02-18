package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class modifyActivityPopup implements Initializable {

    private int activityId;

    // Method to receive the ID
    public void setActivityId(int activityId) {

        this.activityId = activityId;
        // You can use this ID to perform further actions in your controller
    }

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

    @FXML
    private TextField prenomTextField1;

    private ServiceActivity serviceActivity = new ServiceActivity();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Activity ID from activity POPUP:"+this.activityId);
            Activity activity = serviceActivity.findById(activityId);
            if (activity != null) {
                Title.setText(activity.getTitle());
                Description.setText(activity.getDescription());
                Date.setValue(LocalDate.parse(activity.getDate().toLocaleString()));
                prenomTextField1.setText(String.valueOf(activity.getId()));
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init(int activityId) {
        this.activityId = activityId;
        loadData();
    }

    private void loadData() {
        try {
            Activity activity = serviceActivity.findById(activityId);
            if (activity != null) {Title.setText(activity.getTitle());
                Type.setText(activity.getTypeActivity());
                Description.setText(activity.getDescription());
                Price.setText(String.valueOf(activity.getPrice()));
                Date.setValue(LocalDate.parse(activity.getDate().toString()));
                prenomTextField1.setText(String.valueOf(activity.getId()));
            } else {
                // Handle if activity is not found
                System.out.println("Activity non trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur");
        }
    }

    @FXML
    void modifierUtilisateur(ActionEvent event) {
        try {
            String title = Title.getText();
            String type = Type.getText();
            String description = Description.getText();
            double price = Double.parseDouble(Price.getText());
            Date date = java.sql.Date.valueOf(Date.getValue());
            int id = Integer.parseInt(prenomTextField1.getText());

            serviceActivity.update(new Activity(id, date, type, title, description, price));

            showSuccessMessage("Activité Modifiée avec succées");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                try {
                    RouterController router = new RouterController();
                    router.navigate("/fxml/ActivitiesCRUD.fxml");
                    System.out.println("Button Clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void returnTo(MouseEvent mouseEvent) {
        System.out.println("RETURN TO EXECUTED");
        RouterController router;
        router = new RouterController();
        router.navigate("/fxml/ActivitiesCRUD.fxml");
    }
}
