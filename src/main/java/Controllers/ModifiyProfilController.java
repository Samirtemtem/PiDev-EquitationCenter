package Controllers;


import Entities.Users;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifiyProfilController implements Initializable {
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
  private PasswordField Password;

  @FXML
    private TextField Prenom;

    @FXML
    private ComboBox<String> Type;

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
  private PasswordField confirmpass;




  @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;

    @FXML
    private Label prenomLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     //  Type.getItems().addAll(Role.values());
        loadData();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to dahsbord");
                RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
            }
        });
    }




    public void init(int userId) {
        this.userId = userId;
        loadData();
    }
    private File selectedImageFile;
    public boolean validateInputsAndProceed() {

        if (Nom.getText().isEmpty()) {
            showAlert("Le nom est requis");
            return false;
        }



        if (Prenom.getText().isEmpty()) {
            showAlert("Le prénom est requis");
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
        if (password.length() < 8) {
            showAlert("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }
      if (password.length() < 8) {
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
    void ModifiyUser(ActionEvent event) {
        if(!validateInputsAndProceed()) return;
        String nom = Nom.getText();
        String prenom = Prenom.getText();
        String addres = address.getText();
        String Num_tel = num_tel.getText();
        String email = Email.getText();
        String password  = Password.getText();
        String confirmPass=confirmpass.getText();
       // Role type = Type.getValue(); // Get the selected item from ComboBox
        java.util.Date dateJoined = java.sql.Date.valueOf(Date.getValue());
        System.out.println(nom+prenom);
        try {
            // Update

            Users updatedUser = new Users(this.userId,email,password,nom,prenom,addres,Num_tel,confirmPass,dateJoined,"[\"ROLE_CLIENT\"]",imageData);
           // updatedUser.setRoles(Users.Roles.valueOf("ROLE_CLIENT"));
            serviceUser.update(updatedUser);
            showSuccessMessage("Votre profil a été modifiée avec succées");
            MouseEvent e=null;
            this.returnTo(e);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void returnTo(MouseEvent event) {


    }
    private void loadData() {
        try {
            Users user =serviceUser.findById(GuiLoginController.user.getId());
            if (user != null) {
                System.out.println(user);
                Nom.setText(user.getName());

                Prenom.setText(user.getLastName());
                java.util.Date date = user.getDateJoined();
                Date.setValue(LocalDate.parse(user.getDateJoined().toString()));
                confirmpass.setText(user.getPassword());
                num_tel.setText(user.getNum_tel());
                address.setText(user.getAddress());
                Email.setText(user.getEmail());
                Password.setText(user.getPassword());

                imageData = user.getImageData(); // Load image data
                if (imageData != null && imageData.length > 0) {
                    imageView.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(user.getImageData())));
                }
            } else {
                System.out.println("user not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    @FXML
    void uploadImage(Event event) {
        System.out.println("TEST");
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

