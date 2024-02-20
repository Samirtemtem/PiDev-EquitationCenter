package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ActivitiesCrudController {

    @FXML
    private TableView<Activity> tableView; // Assuming you've set this ID in your FXML file

    private final ServiceActivity serviceActivity = new ServiceActivity();
    @FXML
    private ImageView imageView;

    private byte[] imageData;
    @FXML
    public void initialize() {
        initializeTableColumns();
        updateActivityList();
    }
    public void updateActivityList() {
        try {
            List<Activity> activities = serviceActivity.ReadAll();

            tableView.getItems().clear();

            tableView.getItems().addAll(activities);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Activity, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Activity, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Activity, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeActivity"));

        TableColumn<Activity, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Activity, Double> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Activity, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Activity, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        TableColumn<Activity, Void> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellFactory(getImageCellFactory());
          tableView.getColumns().addAll(idColumn, dateColumn, typeColumn, titleColumn, priceColumn, descriptionColumn, actionColumn,imageColumn);
    }

    private Callback<TableColumn<Activity, Void>, TableCell<Activity, Void>> getImageCellFactory() {
        return new Callback<TableColumn<Activity, Void>, TableCell<Activity, Void>>() {
            @Override
            public TableCell<Activity, Void> call(TableColumn<Activity, Void> param) {
                return new TableCell<Activity, Void>() {
                    private final ImageView imageView = new ImageView();

                    {
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        setGraphic(imageView);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Activity activity = getTableView().getItems().get(getIndex());

                            if (activity != null && activity.getImageData() != null) {
                                Image image = new Image(new ByteArrayInputStream(activity.getImageData()));
                                imageView.setImage(image);

                            } else {
                                imageView.setImage(null);
                            }
                        }
                    }
                };
            }
        };
    }
    private Callback<TableColumn<Activity, Void>, TableCell<Activity, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Activity, Void>, TableCell<Activity, Void>>() {
            @Override
            public TableCell<Activity, Void> call(final TableColumn<Activity, Void> param) {
                final TableCell<Activity, Void> cell = new TableCell<Activity, Void>() {
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

                        // Set button actions
                        modifyButton.setOnAction((ActionEvent event) -> {
                            Activity activity = getTableView().getItems().get(getIndex());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Activity activity = getTableView().getItems().get(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton); // Add buttons to HBox

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
                                Activity activity = getTableView().getItems().get(getIndex());
                                if (activity != null) {
                                    System.out.println("SETTING ID");
                                    RouterController.navigate("/fxml/modifyActivityPopup.fxml", activity.getId());

                                } else {
                                    System.err.println("No activity selected.");
                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                Activity activity = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Delete Activity");
                                alert.setContentText("Vous etes sur tu veux supprimer cette activité?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        serviceActivity.delete(activity);

                                        updateActivityList();
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
            public void searchquery(KeyEvent keyEvent) {

            }

            public void gotoAjouter(ActionEvent actionEvent) {
               RouterController router=new RouterController();
               router.navigate("/fxml/AddActivity.fxml");
            }
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/AdminDashboard.fxml");
    }
    public void returnTo(MouseEvent mouseEvent)
    {

    }
        };
