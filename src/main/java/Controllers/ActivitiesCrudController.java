package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class ActivitiesCrudController {

    @FXML
    private TableView<Activity> tableView; // Assuming you've set this ID in your FXML file

    private final ServiceActivity serviceActivity = new ServiceActivity();

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTableColumns();
        updateActivityList();
        // Call method to retrieve and display activities
    }
    public void updateActivityList() {
        try {
            // Retrieve all activities from the service
            List<Activity> activities = serviceActivity.ReadAll();

            // Clear existing items in the TableView
            tableView.getItems().clear();

            // Add retrieved activities to the TableView
            tableView.getItems().addAll(activities);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }


    private void initializeTableColumns() {
        // Clear existing columns
        tableView.getColumns().clear();

        // Add columns for activity properties
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

        // Add column for modify and delete buttons
        TableColumn<Activity, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        // Add columns to TableView
        tableView.getColumns().addAll(idColumn, dateColumn, typeColumn, titleColumn, priceColumn, descriptionColumn, actionColumn);
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

                        // Optionally remove focus border:
                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        // Set button actions
                        modifyButton.setOnAction((ActionEvent event) -> {
                            Activity activity = getTableView().getItems().get(getIndex());
                            // Handle modify action here
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Activity activity = getTableView().getItems().get(getIndex());
                            // Handle delete action here
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
            public void searchquery(KeyEvent keyEvent) {
            }

            public void gotoAjouter(ActionEvent actionEvent) {
            }
        };
