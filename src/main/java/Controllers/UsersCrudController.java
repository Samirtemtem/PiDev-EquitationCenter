package Controllers;
import Entities.Activity;
import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersCrudController implements Initializable {
    private final ServiceUser serviceUser = new ServiceUser();
    @FXML
    private ImageView imageView;

    private byte[] imageData;

    @FXML
    private AnchorPane bord;

    @FXML
    private Button btnAjouter;

    @FXML
    private Label nomPrenom;

    @FXML
    private TextField search_tv;

    @FXML
    private TableView<User> tableView;



    @FXML
    void goToNavigate(ActionEvent event) {
        RouterController router=new RouterController();
        router.navigate("/fxml/Admin/AdminDashboard.fxml");
    }

    @FXML
    void gotoAjouter(ActionEvent event) {
        RouterController router=new RouterController();
        router.navigate("/fxml/Admin/AddUser.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        updateUserList();
    }

    public void updateUserList() {
        try {
            List<User> users = serviceUser.ReadAll();

            tableView.getItems().clear();

            tableView.getItems().addAll(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<User, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User, Void> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellFactory(getImageCellFactory());
        TableColumn<User, Void> RoleColumn = new TableColumn<>("Role");
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
        TableColumn<User, String> prenomColumn = new TableColumn<>("Prenom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<User, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

    //    TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
      //  passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        TableColumn<User, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));

        TableColumn<User, String> Num_telColumn = new TableColumn<>("Num_tel");
        Num_telColumn.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));
        TableColumn<User, String> dateJoinedColumn = new TableColumn<>("dateJoined");
        dateJoinedColumn.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        tableView.getColumns().addAll(idColumn,imageColumn,RoleColumn,nomColumn, prenomColumn,emailColumn, addressColumn, Num_telColumn,dateJoinedColumn,actionColumn);
    }
    private Callback<TableColumn<User, Void>, TableCell<User, Void>> getImageCellFactory() {
        return new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
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
                            User user = getTableView().getItems().get(getIndex());

                            if (user != null && user.getImageData() != null) {
                                Image image = new Image(new ByteArrayInputStream(user.getImageData()));
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
    private Callback<TableColumn<User, Void>, TableCell<User, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
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
                            User user = getTableView().getItems().get(getIndex());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
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
                                User user = getTableView().getItems().get(getIndex());
                                if (user != null) {
                                    System.out.println("SETTING ID");
                                    RouterController.navigate("/fxml/Admin/modifyUser.fxml", user.getId());

                                } else {
                                    System.err.println("No user selected.");
                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                User user = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Delete User");
                                alert.setContentText("Vous etes sur ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        serviceUser.delete(user);

                                        updateUserList();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setTitle("Error");
                                        errorAlert.setHeaderText("Error Base des données");
                                        errorAlert.setContentText("Un erreur en supprimant user.");
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
}