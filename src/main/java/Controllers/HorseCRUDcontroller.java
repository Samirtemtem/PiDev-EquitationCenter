package Controllers;

import Entities.Activity;
import Entities.Horse;
import Service.HorseService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public class HorseCRUDcontroller {

    @FXML
    private TableView<Horse> tableView;

    @FXML
    private TextField search_tv;

    private final HorseService horseService = new HorseService();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateHorseList();
    }

    public void updateHorseList() {
        try {
            List<Horse> horses = horseService.ReadAll();
            tableView.getItems().clear();
            tableView.getItems().addAll(horses);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    @FXML
    private void sortHorsesByName() {
        try {
            // Récupérer la liste des chevaux depuis le service
            List<Horse> sortedHorses = horseService.ReadAll()
                    .stream()
                    .sorted(Comparator.comparing(Horse::getName)) // Tri par le nom
                    .collect(Collectors.toList());

            // Effacer la table et ajouter les chevaux triés
            tableView.getItems().clear();
            tableView.getItems().addAll(sortedHorses);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors du tri des chevaux par nom.");
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Horse, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Horse, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Horse, String> datePensionColumn = new TableColumn<>("Date Pension");
        datePensionColumn.setCellValueFactory(new PropertyValueFactory<>("datePension"));

        TableColumn<Horse, String> breedColumn = new TableColumn<>("Race");
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));

        TableColumn<Horse, Boolean> isAvailableColumn = new TableColumn<>("Disponibilité");
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));


        TableColumn<Horse, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());


        tableView.getColumns().addAll(idColumn, nameColumn, datePensionColumn, breedColumn, isAvailableColumn,actionColumn);
    }

    @FXML
    public void searchquery(javafx.scene.input.KeyEvent keyEvent) {

    }
    private Callback<TableColumn<Horse, Void>, TableCell<Horse, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Horse, Void>, TableCell<Horse, Void>>() {
            @Override
            public TableCell<Horse, Void> call(final TableColumn<Horse, Void> param) {
                final TableCell<Horse, Void> cell = new TableCell<Horse, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);


                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);


                        modifyButton.setOnAction((ActionEvent event) -> {
                            Horse horse = getTableView().getItems().get(getIndex());

                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Horse horse = getTableView().getItems().get(getIndex());

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton);

                            modifyButton.setFocusTraversable(false);
                            deleteButton.setFocusTraversable(false);

                            modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                            deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");


                            Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                            ImageView modifyIcon = new ImageView(modifyImage);
                            modifyIcon.setFitWidth(20);
                            modifyIcon.setFitHeight(20);
                            modifyButton.setGraphic(modifyIcon);
                            Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                            ImageView deleteIcon = new ImageView(deleteImage);
                            deleteIcon.setFitWidth(20);
                            deleteIcon.setFitHeight(20);
                            deleteButton.setGraphic(deleteIcon);
                            modifyButton.setOnAction((ActionEvent event) -> {

                                Horse horse = getTableView().getItems().get(getIndex());
                                System.out.println("activity ID from controller:"+horse.getId());

                                if (horse != null) {
                                    System.out.println("SETTING ID");
                                    RouterController.navigate("/fxml/Vet/ModifierHorse.fxml", horse.getId());


                                } else {
                                    System.err.println("No activity selected.");

                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                Horse horse = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Delete Activity");
                                alert.setContentText("Vous etes sur tu veux supprimer cette activité?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        HorseService horseService1 = new HorseService();
                                        horseService1.delete(horse);

                                        updateHorseList();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setTitle("Error");
                                        errorAlert.setHeaderText("Error Base des données");
                                        errorAlert.setContentText("Un erreur en supprimant l'activité.");
                                        errorAlert.showAndWait();
                                    }
                                }
                            });

                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    public void gotoAjouter(ActionEvent actionEvent) {
    RouterController r= new RouterController();
    r.navigate ("/fxml/Vet/AddHOrse.fxml");

    }

    public void gohome(ActionEvent actionEvent) {
        RouterController r= new RouterController();
        r.navigate ("/fxml/Vet/VetDashboard.fxml");
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
