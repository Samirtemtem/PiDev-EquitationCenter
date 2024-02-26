package Controllers;

import Entities.Activity;
import Entities.Post;
import Service.ServiceActivity;
import Service.ServicePost;
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
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class ControllerCrudPost {

    @FXML
    private TableView<Post> tableView; // Assuming you've set this ID in your FXML file

    private final ServicePost servicePost = new ServicePost();

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTableColumns();
        updatePostList();
        // Call method to retrieve and display activities
    }
    public void updatePostList() {
        try {
            // Retrieve all activities from the service
            List<Post> posts = servicePost.ReadAll();

            // Clear existing items in the TableView
            tableView.getItems().clear();

            // Add retrieved activities to the TableView
            tableView.getItems().addAll(posts);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }


    private void initializeTableColumns() {
        // Clear existing columns
        tableView.getColumns().clear();

        // Add columns for activity properties
        TableColumn<Post, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Post, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Post, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Post, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableView.getColumns().addAll(idColumn, dateColumn, descriptionColumn,actionColumn);
    }
    private Callback<TableColumn<Post, Void>, TableCell<Post, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Post, Void>, TableCell<Post, Void>>() {
            @Override
            public TableCell<Post, Void> call(final TableColumn<Post, Void> param) {
                final TableCell<Post, Void> cell = new TableCell<Post, Void>() {
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
                            Post post = getTableView().getItems().get(getIndex());
                            System.out.println(post.getId());
                            RouterController.navigate("/fxml/ModifyPost.fxml", post.getId());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Post post = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Post");
                            alert.setContentText("Vous etes sur tu veux supprimer cette Actualité?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServicePost r=new ServicePost();
                                    r.delete(post);

                                    updatePostList();
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

    public void searchquery(KeyEvent keyEvent) {
    }

    public void gotoAjouter(ActionEvent actionEvent) {
        RouterController r=new RouterController();
        r.navigate("../fxml/AddPost.fxml");
    }
    public void AddPost(ActionEvent actionEvent){
        System.out.println("Testing link");
        RouterController r=new RouterController();
        r.navigate("../fxml/AddPost.fxml");
    }


};
