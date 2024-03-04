package Controllers;

import Entities.*;
import Service.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddActivityController {

    @FXML
    private TextField Title;

    @FXML
    private TextField Price;

    @FXML
    private DatePicker Date;
    @FXML
    private JFXComboBox<Horse> horseComboBox;

    @FXML
    private JFXComboBox<User> userComboBox;

    private ServiceHorse serviceHorse = new ServiceHorse();
    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    private TextArea Description;

    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    private ServiceActivity serviceActivity = new ServiceActivity();

    @FXML
    private ImageView btnReturn;
    @FXML
    private JFXComboBox<ActivityType> Type;
    @FXML
    public void initialize() {
        populateHorseComboBox();
        populateUserComboBox();
        ObservableList<ActivityType> activityTypes = FXCollections.observableArrayList(ActivityType.values());
        System.out.println(ActivityType.values().toString());
        Type.setItems(activityTypes);
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to Activities CRUD");
                RouterController.navigate("/fxml/Activities/ActivitiesCRUD.fxml");
            }
        });
    }
    private void populateHorseComboBox() {
        List<Horse> horses = null;
        try {
            horses = serviceHorse.ReadAvailableHorses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Horse> horseList = FXCollections.observableArrayList(horses);
        horseComboBox.setItems(horseList);
    }

    private void populateUserComboBox() {
        List<User> users = null;
        try {
            users = serviceUser.ReadInstructors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<User> userList = FXCollections.observableArrayList(users);
        userComboBox.setItems(userList);
    }

    public boolean validateInputsAndProceed() {
        if (
        selectedImageFile == null) {
            showAlert("Selectionner une image stp.");
            return false;
        }
        if (Title.getText().isEmpty()) {
            showAlert("Titre ne peux pas etre vide");
            return false;
        }
        if (Price.getText().isEmpty()) {
            showAlert("Price ne peux pas etre vide");
            return false;
        }

        try {
            Double.parseDouble(Price.getText());
        } catch (NumberFormatException e) {
            showAlert("Price ne peux pas etre vide");
            return false;
        }

        if (Date.getValue() == null) {
            showAlert("Date ne peux pas etre vide");
            return false;
        }

        if (Description.getText().isEmpty()) {
            showAlert("Description ne peux pas etre vide");
            return false;
        }

        try {
            double price = Double.parseDouble(Price.getText());
            if (price <= 0) {
                showAlert("Le prix doit être un nombre positif.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre valide.");
            return false;
        }

        if (Date.getValue() == null) {
            showAlert("La date ne peut pas être vide.");
            return false;
        }

        if (Date.getValue().isBefore(LocalDate.now())) {
            showAlert("La date ne peut pas être dans le passé.");
            return false;
        }

        if (Description.getText().isEmpty()) {
            showAlert("La description ne peut pas être vide.");
            return false;
        }

        return true;

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void AddActivity(ActionEvent actionEvent) {
        if(!validateInputsAndProceed()) return;
        Horse selectedHorse = horseComboBox.getValue();
        User selectedUser = userComboBox.getValue();

        if (selectedHorse == null) {
            showAlert("Veuillez sélectionner un cheval.");
            return;
        }

        if (selectedUser == null) {
            showAlert("Veuillez sélectionner un utilisateur.");
            return;
        }
        ServiceHorseActivity serviceHorseActivity=new ServiceHorseActivity();
        UserActivityService userActivityService=new UserActivityService();
        String title = Title.getText();
        ActivityType type = Type.getValue();
        double price = Double.parseDouble(Price.getText());
        Date date = java.sql.Date.valueOf(Date.getValue());
        String description = Description.getText();

        try {
            byte[] imageData = null;
            if (selectedImageFile != null) {
                imageData = loadImage(selectedImageFile);
            }

            Activity activity = new Activity();
            activity.setTitle(title);

            activity.setTypeActivity(type);
            activity.setPrice(price);
            activity.setDate(date);
            activity.setDescription(description);
            activity.setImageData(imageData);

            int id=serviceActivity.add(activity,"return id");
            HorseActivity horseActivity=new HorseActivity(selectedHorse.getId(),id);
            UserActivity userActivity=new UserActivity(selectedUser.getId(), id,"",0);
            userActivityService.add(userActivity);
            serviceHorseActivity.add(horseActivity);
            showSuccessMessage("Nouvelle activitée ajouté avec succées");
            returnTo(actionEvent);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private byte[] loadImage(File file) throws FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];
            fis.read(imageData);
            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Error loading image file");
        }
    }
    @FXML
    public void returnTo(ActionEvent actionEvent) {
        System.out.println("Returning to Activities CRUD");
        RouterController.navigate("/fxml/Activities/ActivitiesCRUD.fxml");
    }

    @FXML
    public void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);
        if (selectedImageFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
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
