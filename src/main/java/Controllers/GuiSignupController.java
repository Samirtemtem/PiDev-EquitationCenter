package Controllers;





import Entities.Role;
import Entities.User;
import Service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GuiSignupController implements Initializable {

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
    private ComboBox<Role> Type;

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
    private ImageView imageView;

    @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;



    private File selectedImageFile;

    private ServiceUser serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public boolean validateInputsAndProceed() {
        if (
                selectedImageFile == null) {
            showAlert("Selectionner une image stp.");
            return false; // Stop the process
        }
        if (Nom.getText().isEmpty()) {
            showAlert("Le nom est requis");
            return false;
        }



        if (Prenom.getText().isEmpty()) {
            showAlert("Le prénom est requis");
            return false;
        }
        if (address.getText().isEmpty()) {
            showAlert("Address est requis");
            return false;
        }
        if (num_tel.getText().isEmpty()) {
            showAlert("Numéro de téléphone est requis");
            return false;
        }
        if (Email.getText().isEmpty()) {
            showAlert("Email est requis");
            return false;
        }

        if (Date.getValue() == null) {
            showAlert("Date est requis");

            return false;
        }

        if (Password.getText().isEmpty()) {
            showAlert("password est requiq");
            return false;
        }
        String password  = Password.getText();
        if (password.length() < 8 ) {
            showAlert("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }

        if (confirmpass.getText().isEmpty()) {
            showAlert("La confirmation du mot de passe est requise");
            return false;
        }
        // Vérifier si l'adresse email est valide et afficher une erreur si elle ne l'est pas
        String email = Email.getText();
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("L'adresse email est invalide");
            return false;
        }
// Vérifier si le mot de passe et sa confirmation correspondent et afficher une erreur si ce n'est pas le cas
        if (Password.equals(confirmpass)){
            showAlert("Les mots de passe ne correspondent pas");
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
    void signup(ActionEvent event) {
        if(!validateInputsAndProceed()) return;
        String nom = Nom.getText();
        String prenom = Prenom.getText();
        String addres = address.getText();
        String Num_tel = num_tel.getText();
        String email = Email.getText();
        String password  = Password.getText();
        String confirmPass=confirmpass.getText();
        java.util.Date date = java.sql.Date.valueOf(Date.getValue());
        //Role type = Type.getValue("Client");// Valeur par défaut

        try {
            // Upload image if selected
            byte[] imageData = null;
            if (selectedImageFile != null) {
                imageData = loadImage(selectedImageFile);
            }
            // Create an Activity object
            User user = new User();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setDateJoined(date);
            user.setAddress(addres);
            user.setNum_tel(Num_tel);
            user.setEmail(email);
            user.setPassword(password);
            user.setImageData(imageData);
            user.setRole(Role.valueOf("CLIENT"));
            serviceUser.add(user);
            showSuccessMessage("Votre inscription a été enregistrée avec succès");
            returnTo(event);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
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
        System.out.println("Returning to ");
        RouterController.navigate("/fxml/Home.fxml");
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



