package Controllers;
import Controllers.components.BadWordFilter;
import Entities.Complaint;
import Entities.MedicalVisit;
import Entities.Role;
import Entities.User;
import Service.ServiceComplaint;
import Service.ServiceUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComplaintController implements Initializable {
  @FXML
  private TableColumn<?, ?> Description;

  @FXML
  private TableColumn<?, ?> Objet;

  @FXML
  private AnchorPane bord;


  @FXML
  private Button btnNaviguer;

  @FXML
  private Button btnSupprimer;
  @FXML
  private TableColumn<Complaint, Integer> idColumn;


  @FXML
  private TableColumn<Complaint, String> objetColumn;

  @FXML
  private TableColumn<Complaint, String> descriptionColumn;

  @FXML
  private TableColumn<Complaint, String> createdAtColumn;

  @FXML
  private TableColumn<Complaint, String> etatColumn;
  @FXML
  private TableColumn<Complaint, String> answer;
  @FXML
  private Button btnValider;

  @FXML
  private Button btnmodifier;

  @FXML
  private TableColumn<?, ?> createdAt;

  @FXML
  private TableColumn<?, ?> etat;

  @FXML
  private TableColumn<?, ?> id;

  @FXML
  private Label nomPrenom3;

  @FXML
  private TableView<Complaint> tableReclamation;
  @FXML
  private DatePicker date;
  @FXML
  private VBox vbox2;
  private String actionForm="ajouter";


  @FXML
  private TextField textFieldDescription;

  @FXML
  private TextField textFieldObjet;

  @FXML
  private DatePicker datePickerCreatedAt;
  @FXML
  void goToNavigate(ActionEvent event) {
    RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
  }

  @FXML
  void goToReclamation(ActionEvent event) {
      RouterController.navigate("/fxml/Client/Complaint.fxml");
  }

  @FXML
  void remplirModifierform_backup(ActionEvent event) {

    this.actionForm="modifier";
    Complaint recSelected = (Complaint) tableReclamation.getSelectionModel().getSelectedItem();
    System.out.println(recSelected.getCreatedAt());
    createdAt.setText(recSelected.getCreatedAt().toString());
    Objet.setText(recSelected.getObjet());
    Description.setText(recSelected.getDescription());
    answer.setText(recSelected.getAnswer());

  }

  public void Refresh() throws SQLException {
    ServiceComplaint rc  = new ServiceComplaint();
    ObservableList<Complaint> list = (ObservableList<Complaint>) rc.ReadAll();
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    objetColumn.setCellValueFactory(new PropertyValueFactory<>("objet"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
    answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
    tableReclamation.setItems(list);
    TableColumn<Complaint, Void> actionColumn = new TableColumn<>("Action");
    actionColumn.setCellFactory(getButtonCellFactory());
    tableReclamation.getColumns().add(actionColumn);



  }
  private Callback<TableColumn<Complaint, Void>, TableCell<Complaint, Void>> getButtonCellFactory() {
    return new Callback<TableColumn<Complaint, Void>, TableCell<Complaint, Void>>() {
      @Override
      public TableCell<Complaint, Void> call(final TableColumn<Complaint, Void> param) {
        final TableCell<Complaint, Void> cell = new TableCell<Complaint, Void>() {
          private final Button answer = new Button();

          {
            Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
            ImageView modifyIcon = new ImageView(modifyImage);
            modifyIcon.setFitWidth(20);
            modifyIcon.setFitHeight(20);
            answer.setGraphic(modifyIcon);

            answer.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
            answer.setOnAction((ActionEvent event) -> {
              Complaint complaint = getTableView().getItems().get(getIndex());
              showPrompt(complaint);
            });



          }

          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              HBox buttons = new HBox(5);
              buttons.getChildren().addAll(answer); // Add buttons to HBox

              answer.setFocusTraversable(false);

              setGraphic(buttons);
            }
          }
        };
        return cell;
      }
    };
  }

  @FXML
  private void supprimerReclamation(ActionEvent event) {
    Complaint selectedComplaint = tableReclamation.getSelectionModel().getSelectedItem();
    if (selectedComplaint != null) {
      try {
        ServiceComplaint sc=new ServiceComplaint();
        sc.delete(selectedComplaint);
        tableReclamation.getItems().remove(selectedComplaint);
        System.out.println("Reclamation supprimée !");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Veuillez sélectionner une réclamation à supprimer.");
    }
  }

  @FXML
  private void remplirModifierform(ActionEvent event) throws SQLException {
    Complaint selectedComplaint = tableReclamation.getSelectionModel().getSelectedItem();
    if (selectedComplaint != null) {
      ServiceUser su=new ServiceUser();
      textFieldDescription.setText(selectedComplaint.getDescription());
      textFieldObjet.setText(selectedComplaint.getObjet());
      java.util.Date sqlDate = selectedComplaint.getCreatedAt();
      java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
      LocalDate createdAt = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      datePickerCreatedAt.setValue(createdAt);


      btnValider.setText("Modifier");
      btnValider.setOnAction(this::modifierReclamation);

      btnSupprimer.setDisable(true);
      btnmodifier.setDisable(true);
    } else {
      System.out.println("Veuillez sélectionner une réclamation à modifier.");
    }
  }
  private void modifierReclamation(ActionEvent event) {
    Complaint selectedComplaint = tableReclamation.getSelectionModel().getSelectedItem();
    if (selectedComplaint != null) {
      selectedComplaint.setUserId(getSelectedUserId().getId());
      selectedComplaint.setDescription(textFieldDescription.getText());
      selectedComplaint.setObjet(textFieldObjet.getText());
      selectedComplaint.setCreatedAt(java.sql.Date.valueOf(datePickerCreatedAt.getValue()));

      try {
        ServiceComplaint sc = new ServiceComplaint();
        sc.update(selectedComplaint);
        Refresh();
        btnValider.setText("Valider");
        btnValider.setOnAction(event1 -> {
          try {
            saveReclamation(event1);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
        btnSupprimer.setDisable(false);
        btnmodifier.setDisable(false);
      } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
      }
    } else {
      System.out.println("Veuillez sélectionner une réclamation à modifier.");
    }
  }

  private User getSelectedUserId() {
   return GuiLoginController.user;
  }




  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      Refresh();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public boolean validateInputsAndProceed() {

    if (textFieldObjet.getText().isEmpty()) {
      showAlert("Object est requis");
      return false;
    }
    if (textFieldDescription.getText().isEmpty()) {
      showAlert("Description est requis");
      return false;
    }
    if (datePickerCreatedAt.getValue() == null) {
      showAlert("Date est requis");

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
  void saveReclamation(ActionEvent event) throws Exception {

    ServiceUser serviceUser = new ServiceUser();
    User selectedUser = getSelectedUserId();


    if (selectedUser == null) {
      return;
    }
    if(!validateInputsAndProceed()) return;
    String description = BadWordFilter.filterText(textFieldDescription.getText().trim());

    String objet = textFieldObjet.getText();
    java.sql.Date createdAt = java.sql.Date.valueOf(datePickerCreatedAt.getValue());
    String etat = "En cours"; // Valeur par défaut

    Complaint newComplaint = new Complaint(selectedUser.getId(), objet, description, etat, createdAt);

    try {
      ServiceComplaint sc=new ServiceComplaint();
      sc.add(newComplaint);
      Refresh();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  @FXML
  public void returnTo(ActionEvent actionEvent) {
    System.out.println("Returning to clientDashbord");
    RouterController.navigate("/fxml/ClientDashboard.fxml");
  }

    public void goToActivities(MouseEvent mouseEvent) {
    }
  private void showPrompt(Complaint complaint) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Répondre à la réclamation");
    dialog.setHeaderText("Répondre à la réclamation #" + complaint.getId());
    dialog.setContentText("Entrez votre réponse:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(response -> {
      System.out.println("Réponse pour la réclamation #" + complaint.getId() + ": " + response);
      complaint.setAnswer(response);
      ServiceComplaint sc=new ServiceComplaint();
      try {
        sc.update(complaint);
        this.Refresh();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
