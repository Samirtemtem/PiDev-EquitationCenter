package Controllers;
import Entities.Complaint;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ComplaintController implements Initializable {
  @FXML
  private TableColumn<?, ?> Description;

  @FXML
  private TableColumn<?, ?> Objet;

  @FXML
  private AnchorPane bord;

  @FXML
  private ComboBox<String> comboBoxUsers;

  @FXML
  private Button btnNaviguer;

  @FXML
  private Button btnSupprimer;
  @FXML
  private TableColumn<Complaint, Integer> idColumn;

  @FXML
  private TableColumn<Complaint, Integer> userIdColumn;

  @FXML
  private TableColumn<Complaint, String> objetColumn;

  @FXML
  private TableColumn<Complaint, String> descriptionColumn;

  @FXML
  private TableColumn<Complaint, String> createdAtColumn;

  @FXML
  private TableColumn<Complaint, String> etatColumn;
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
  void goToNavigate(ActionEvent event) {/*
    System.out.println("enter0");
    Complaint recSelected = (Complaint) tableReclamation.getSelectionModel().getSelectedItem();
    ServiceReponse rp = new ServiceReponse();
    Reponse r= rp.getReponseByIdReclamation(recSelected.getId());
    reponsevalue.setText(r.getContenuRep());
    System.out.println(r.getContenuRep());*/

  }

  @FXML
  void goToReclamation(ActionEvent event) {

  }

  @FXML
  void remplirModifierform_backup(ActionEvent event) {

    this.actionForm="modifier";
    Complaint recSelected = (Complaint) tableReclamation.getSelectionModel().getSelectedItem();
    System.out.println(recSelected.getCreatedAt());
    createdAt.setText(recSelected.getCreatedAt().toString());
    Description.setText(recSelected.getDescription());
    Objet.setText(recSelected.getObjet());
  }
 /*
  @FXML
  void saveReclamation(ActionEvent event) {
    /*
    ServiceComplaint rc  = new ServiceComplaint();
    String TypeR =tfTypeR.getText();
    String Description =tfDescription.getText();
    String objet =tfObjet.getText();
    String tell=numtell.getText();
    //  verifBadWord(Description);
    if(TypeR.length()==0||Description.length()==0||objet.length()==0||tell.length()==0){
      erreurPane.setVisible(true);
      erreurvalue.setText("Tous les champs sont obligatoires ");
    }else if(tell.length()!=8||isNumeric(tell)==false){

      erreurPane.setVisible(true);
      erreurvalue.setText("Contact doit etre une chaine numérique de 8 caractères ");
    }else{

      erreurvalue.setText("");
      erreurPane.setVisible(false);


      if(actionForm.compareTo("ajouter")==0){

        String etat ="nonresolu";

        Complaint r = new Complaint(0,TypeR,Description,objet,etat,idUser);
        rc.ajouterReclamation(r);

    try{
      Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+216"+numtell.getText()),
                new com.twilio.type.PhoneNumber("+12766246381"),
                "Votre réclamation a été ajoutée avec succès. Nous allons l'examiner dès que possible et vous contacterons si nous avons besoin de plus d'informations. Merci de nous avoir contacté.")
            .create();
    }catch(Exception e){
        System.out.print("erreur"+e.getMessage());
    }
        Refresh();

      }else{*/
        /* Complaint recSelected = (Complaint) tableReclamation.getSelectionModel().getSelectedItem();

        recSelected.setDescription(tfDescription.getText());
        recSelected.setObjet(tfObjet.getText());
        System.out.print(recSelected);
        rc.Update(recSelected);
        actionForm="ajouter";
        Refresh();

      }
    }
  }*/
  public void Refresh() throws SQLException {
    ServiceComplaint rc  = new ServiceComplaint();
    ObservableList<Complaint> list = (ObservableList<Complaint>) rc.ReadAll();
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    objetColumn.setCellValueFactory(new PropertyValueFactory<>("objet"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));

    btnSupprimer.setOnAction(this::supprimerReclamation);
    btnmodifier.setOnAction(event -> {
      try {
        remplirModifierform(event);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
    tableReclamation.setItems(list);
    tableReclamation.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        btnSupprimer.setDisable(false);
        btnmodifier.setDisable(false);
      } else {
        btnSupprimer.setDisable(true);
        btnmodifier.setDisable(true);
      }
    });

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
      comboBoxUsers.setValue(su.findById(selectedComplaint.getUserId()).getEmail());
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
      selectedComplaint.setUserId(getSelectedUserId());
      selectedComplaint.setDescription(textFieldDescription.getText());
      selectedComplaint.setObjet(textFieldObjet.getText());
      selectedComplaint.setCreatedAt(java.sql.Date.valueOf(datePickerCreatedAt.getValue()));

      try {
        ServiceComplaint sc = new ServiceComplaint();
        sc.update(selectedComplaint);
        Refresh();
        btnValider.setText("Valider");
        btnValider.setOnAction(this::saveReclamation);
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

  private int getSelectedUserId() {
    String selectedEmail = comboBoxUsers.getValue();
    if (selectedEmail == null || selectedEmail.isEmpty()) {
      return 0; // Or handle the case where no email is selected
    }

    try {
      ServiceUser serviceUser = new ServiceUser();
      User user = serviceUser.findByEmail(selectedEmail);
      if (user != null) {
        return user.getId();
      } else {
        return 0; // Or handle the case where user is not found
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return 0; // Or handle the exception as needed
    }
  }



  private void loadUsers() throws SQLException {
    ServiceUser su=new ServiceUser();
    List<User> users = su.ReadAll();
    for (User user : users) {
      comboBoxUsers.getItems().add(user.getEmail());
    }  }
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      loadUsers();
      Refresh();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @FXML
  void saveReclamation(ActionEvent event) {
    String selectedUserEmail = comboBoxUsers.getValue();

    ServiceUser serviceUser = new ServiceUser();
    User selectedUser = null;
    try {
      selectedUser = serviceUser.findByEmail(selectedUserEmail);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (selectedUser == null) {
      return;
    }

    String description = textFieldDescription.getText();
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

}
