package Controllers;

import Entities.Commentaire;
import Service.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class ModifyCommController implements InintializableID {

    @FXML
    private TextArea commentaireTextArea;
    @FXML
    private DatePicker datefield;
    private Commentaire selectedCommentaire;
    private ServiceCommentaire serviceCommentaire;
    private int commentId;

    public void initData(int commentId) {
        serviceCommentaire = new ServiceCommentaire();
        try {
            selectedCommentaire = serviceCommentaire.findById(commentId);
            if (selectedCommentaire != null) {
                commentaireTextArea.setText(selectedCommentaire.getCommentaire());
                datefield.setValue(LocalDate.parse(selectedCommentaire.getDate().toString()));

            } else {
                showErrorAlert("Aucun commentaire trouvé avec l'ID: " + commentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors de la récupération du commentaire avec l'ID: " + commentId);
        }
    }

    @FXML
    public void modifierComm() {
        if (selectedCommentaire != null) {
            String newCommentaireText = commentaireTextArea.getText();
            if (!newCommentaireText.isEmpty()) {
                selectedCommentaire.setCommentaire(newCommentaireText);
                java.util.Date nvdate = java.sql.Date.valueOf(datefield.getValue());
                selectedCommentaire.setDate(nvdate);
                try {
                    serviceCommentaire.update(selectedCommentaire);
                    showSuccessAlert("Commentaire modifié avec succès");
                    MouseEvent e=null;
                    this.returnTo(e);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Erreur lors de la modification du commentaire.");
                }
            } else {
                showErrorAlert("Le commentaire ne peut pas être vide.");
            }
        } else {
            showErrorAlert("Aucun commentaire sélectionné.");
        }
    }

    @FXML
    public void returnTo(MouseEvent e) {
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

    @Override
    public void init(Integer Id) {
        System.out.println("ID FROM CONTROLLER:"+Id);
        this.commentId = Id;
        initData(commentId);
    }
}
