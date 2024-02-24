package Controllers;


import Entities.Activity;
import Entities.User;
import Service.ServiceUser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
 /*
public class ModifyUserController implements Initializable {
    private int userId;
    private byte[] imageData;
    @FXML
    private ImageView imageView;
    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    private DatePicker Date;

    @FXML
    private TextField Email;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Password;

    @FXML
    private TextField Prenom;

    @FXML
    private ComboBox<?> Type;

    @FXML
    private TextField address;

    @FXML
    private Label adresseLabel;

    @FXML
    private AnchorPane bord;

    @FXML
    private ImageView btnReturn;

    @FXML
    private Button btnSignup;

    @FXML
    private Button btnUploadImage;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField confirmpass;



    @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;

    @FXML
    private Label prenomLabel;
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.getItems().addAll(ActivityType.values());
        loadData();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to users CRUD");
                RouterController.navigate("/fxml/UsersCRUD.fxml");
            }
        });
    }
    public void init(int userId) {
        this.userId = userId;
        loadData();
    }

    @FXML
    void ModifiyUser(ActionEvent event) {
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
    void returnTo(MouseEvent event) {

    }
    private void loadData() {
        try {
            User user =serviceUser .findById(userId);
            if (user != null) {
                Title.setText(user.getTitle());
                Type.setValue(ActivityType.valueOf(activity.getTypeActivity().name()));
                Description.setText(user.getDescription());
                Price.setText(String.valueOf(user.getPrice()));
                java.util.Date date = user.getDate();
                Date.setValue(LocalDate.parse(user.getDate().toString()));


                imageData = user.getImageData(); // Load image data
                if (imageData != null && imageData.length > 0) {
                    imageView.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(user.getImageData())));
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
                // Display the selected image
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

     */
