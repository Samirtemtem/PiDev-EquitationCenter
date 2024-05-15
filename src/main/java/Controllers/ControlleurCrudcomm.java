package Controllers;

import Entities.Commentaire;
import Entities.Post;
import Service.ServiceCommentaire;
import Service.ServicePost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControlleurCrudcomm implements Initializable {

    @FXML
    private TableView<Commentaire> tableView;

    @FXML
    private TableColumn<Commentaire, Integer> idColumn;

    @FXML
    private TableColumn<Commentaire, String> dateColumn;

    @FXML
    private TableColumn<Commentaire, String> commentaireColumn;

    private ServiceCommentaire serviceCommentaire;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Commentaire, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        serviceCommentaire = new ServiceCommentaire();
        loadCommentaires();

        tableView.getColumns().addAll(actionColumn);
    }
    @FXML
    private void sortCommentaireButtonClicked() {
        if (commentaireColumn.getSortType() == TableColumn.SortType.DESCENDING) {
            commentaireColumn.setSortType(TableColumn.SortType.ASCENDING);
        } else {
            commentaireColumn.setSortType(TableColumn.SortType.DESCENDING);
        }
        tableView.sort();
    }

    private Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>>() {
            @Override
            public TableCell<Commentaire, Void> call(final TableColumn<Commentaire, Void> param) {
                final TableCell<Commentaire, Void> cell = new TableCell<Commentaire, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        modifyButton.setOnAction((ActionEvent event) -> {
                            Commentaire comm = getTableView().getItems().get(getIndex());
                            System.out.println("MODIFY COMMENTAIRE+"+comm.getId());

                            RouterController.navigate("/fxml/ModifyComm.fxml", comm.getId());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Commentaire comm = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Post");
                            alert.setContentText("Vous etes sur tu veux supprimer cette Actualité?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServiceCommentaire r=new ServiceCommentaire();
                                    r.delete(comm);
                                    loadCommentaires();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setTitle("Error");
                                    errorAlert.setHeaderText("Error Base des données");
                                    errorAlert.setContentText("Un erreur en supprimant le produit.");
                                    errorAlert.showAndWait();
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox buttons = new VBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void loadCommentaires() {
        try {
            ObservableList<Commentaire> commentairesList = FXCollections.observableArrayList(serviceCommentaire.ReadAll());
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            commentaireColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            tableView.setItems(commentairesList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    public void goToActualite(ActionEvent event) {
        RouterController.navigate("/fxml/PostCrud.fxml");
    }

    public void goToComments(ActionEvent event) {
        RouterController.navigate("/fxml/Commcrud.fxml");
    }
    @FXML
    private void gotoAjouter() {
        RouterController.navigate("/fxml/addcomm.fxml");
        // Add logic to navigate to the "Ajouter" view
    }

    public void navigate(ActionEvent event) {
        RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
    }
    public void sortCommentaireButtonClicked(ActionEvent event)
    {
        RouterController.navigate("/fxml/StatsCom.fxml");
    }
}
