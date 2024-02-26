package Controllers;

import Entities.Commentaire;
import Service.ServiceCommentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.Date;

public class AddCommController {

    @FXML
    private TextArea commentaire;

    private ServiceCommentaire serviceCommentaire;

    @FXML
    public void initialize() {
        serviceCommentaire = new ServiceCommentaire();
    }

    @FXML
    public void AddComm(ActionEvent actionEvent) {
        String text = commentaire.getText();
        if (text != null && !text.isEmpty()) {
            Date currentDate = new Date();
            Commentaire newCommentaire = new Commentaire(currentDate, text);
            try {
                serviceCommentaire.add(newCommentaire);
                MouseEvent e=null;
                returnTo(e);
                showSuccessAlert("Commentaire ajouté avec succès");
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur lors de l'ajout du commentaire.");
            }
        } else {
            showErrorAlert("Le commentaire ne peut pas être vide.");
        }
    }

    @FXML
    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Commcrud.fxml");
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
