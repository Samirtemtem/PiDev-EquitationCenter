package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ActivitiesCrudController {

    @FXML
    private Label adminNameLabel;

    @FXML
    private TableView<Activity> tableView;

    private final ServiceActivity serviceActivity = new ServiceActivity();
    @FXML
    private ImageView imageView;

    private byte[] imageData;
    @FXML
    private TextField search_tv;

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
        GuiLoginController guilogin = new GuiLoginController();
        String name="Bienvenue "+guilogin.user.getName()+"!";
        adminNameLabel.setText(name);
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
                                Activity activity = getTableView().getItems().get(getIndex());
                                if (activity != null) {
                                    System.out.println("SETTING ID");
                                    RouterController.navigate("/fxml/Activities/modifyActivityPopup.fxml", activity.getId());

                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                Activity activity = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Supprimer Activité");
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
    private ObservableList<Activity> activitiesList = FXCollections.observableArrayList();

    @FXML
    public void searchquery(KeyEvent keyEvent) throws SQLException {
                    String query = search_tv.getText();
                    ServiceActivity sa=new ServiceActivity();
                    List<Activity> searchedActivities = sa.searchActivities(query);

                        activitiesList.clear();
                        activitiesList.addAll(searchedActivities);
                        tableView.setItems(activitiesList);
                        initializeTableColumns();
                }

            public void gotoAjouter(ActionEvent actionEvent) {
               RouterController router=new RouterController();
               router.navigate("/fxml/Activities/AddActivity.fxml");
            }
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/Admin/AdminDashboard.fxml");
    }
    public void returnTo(MouseEvent mouseEvent)
    {
    }
    public void goToActivities(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/Activities/ActivitiesCRUD.fxml");
    }

    public void GoToActivitySessions(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/ActivitySession/ActivitySessionCRUD.fxml");
    }
};
