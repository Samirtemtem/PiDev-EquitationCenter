package Controllers;

import Entities.Complaint;
import Entities.Reponse;
import Service.ServiceComplaint;
import Service.ServiceReponse;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReponseController implements Initializable {

  @FXML
  private TableView<Complaint> tableComplaint;
  @FXML
  private TableColumn<Complaint, Integer> id;
  @FXML
  private TableColumn<Complaint, String> object;
  @FXML
  private TableColumn<Complaint, String> description;
  @FXML
  private TableColumn<Complaint, String> etat;
  @FXML
  private TableColumn<Complaint, String> createdAt;
  @FXML
  private Pane addReponseForm;
  @FXML
  private Button btnValider;
  @FXML
  private TextArea contenuRep;
  @FXML
  private Button btnAfficherReponse;
  @FXML
  private Pane reponsePane;
  @FXML
  private Label reponselabel;
  private Reponse selectedReponse;
  @FXML
  private Button btnSupprimerReclamation;
  @FXML
  private Button btnStatReclamation;
  @FXML
  private Label nomPrenom3;
  @FXML
  private VBox vbox2;
  @FXML
  private Button btnNaviguer;
  @FXML
  private AnchorPane bord;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      refresh();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    ObservableList<Complaint> selectedCells = tableComplaint.getSelectionModel().getSelectedItems();
    selectedCells.addListener(new ListChangeListener<Complaint>() {
      @Override
      public void onChanged(Change<? extends Complaint> c) {
        Complaint complaintSelected = tableComplaint.getSelectionModel().getSelectedItem();

        if (complaintSelected != null) {
          btnSupprimerReclamation.setDisable(false);
          if (complaintSelected.getEtat().compareTo("nonresolu") == 0) {
            btnAfficherReponse.setDisable(false);
          } else {
            btnAfficherReponse.setDisable(true);
          }
        } else {
          btnSupprimerReclamation.setDisable(true);
          btnAfficherReponse.setDisable(true);
        }
      }

    });
  }

  public void refresh() throws SQLException {
    ServiceComplaint cc = new ServiceComplaint();
    ObservableList<Complaint> list = (ObservableList<Complaint>) cc.ReadAll();
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    object.setCellValueFactory(new PropertyValueFactory<>("object"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
    createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    tableComplaint.setItems(list);
  }

  @FXML
  private void supprimerReclamation(ActionEvent event) throws SQLException {
    Complaint complaintSelected = tableComplaint.getSelectionModel().getSelectedItem();
    ServiceComplaint cc = new ServiceComplaint();
    cc.delete(complaintSelected);
    refresh();
  }

  @FXML
  private void ouvrirRepondreForm(ActionEvent event) {
    reponsePane.setVisible(false);
    addReponseForm.setVisible(true);
  }

  @FXML
  private void saveReponse(ActionEvent event) throws SQLException {
    Complaint complaintSelected = tableComplaint.getSelectionModel().getSelectedItem();
    ServiceReponse cr = new ServiceReponse();
    Reponse reponse = new Reponse(contenuRep.getText(), complaintSelected.getId());
    cr.add(reponse);
    contenuRep.setText("");
    addReponseForm.setVisible(false);
    refresh();
  }

  @FXML
  private void afficherReponse(ActionEvent event) {
    addReponseForm.setVisible(false);
    reponsePane.setVisible(true);
    Complaint complaintSelected = tableComplaint.getSelectionModel().getSelectedItem();
    ServiceReponse cr = new ServiceReponse();
    selectedReponse = cr.getReponseByIdReclamation(complaintSelected.getId());
    reponselabel.setText(selectedReponse.getContenuRep());
  }

  @FXML
  private void supprimerReponse(ActionEvent event) throws SQLException {
    ServiceReponse cr = new ServiceReponse();
    cr.delete(selectedReponse);
    refresh();
    reponsePane.setVisible(false);
  }

  @FXML
  private void gotostat(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminStatReclmation.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException ex) {
      Logger.getLogger(ReponseController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void goToNavigate(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("GuiAdmin.fxml"));
    try {
      Parent root = loader.load();
      bord.getChildren().setAll(root);
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }
}
