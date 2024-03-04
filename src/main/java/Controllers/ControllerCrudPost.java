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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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


import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ControllerCrudPost {

    @FXML
    private TableView<Post> tableView; // Assuming you've set this ID in your FXML file

    private final ServicePost servicePost = new ServicePost();

    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModal;
    @FXML
    private TableColumn<Post, String> descriptionColumn;

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTableColumns();
        updatePostList();
        // Call method to retrieve and display activities

        qrCodeImgModal.setVisible(false);
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

    @FXML
    private void sortDescriptionButtonClicked() {
        if (descriptionColumn.getSortType() == TableColumn.SortType.DESCENDING) {
            descriptionColumn.setSortType(TableColumn.SortType.ASCENDING);
        } else {
            descriptionColumn.setSortType(TableColumn.SortType.DESCENDING);
        }
        tableView.sort();
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
                    private final Button shareButton = new Button();

                    private final Button qrButton = new Button();

                    {
                        Image shareImage = new Image(getClass().getResourceAsStream("../assets/facebook.png"));
                        ImageView shareIcon = new ImageView(shareImage);
                        shareIcon.setFitWidth(20);
                        shareIcon.setFitHeight(20);
                        shareButton.setGraphic(shareIcon);

                        shareButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image qrImage = new Image(getClass().getResourceAsStream("../assets/qr-code.png"));
                        ImageView qrIcon = new ImageView(qrImage);
                        qrIcon.setFitWidth(20);
                        qrIcon.setFitHeight(20);
                        qrButton.setGraphic(qrIcon);

                        qrButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");


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

                        shareButton.setOnAction((ActionEvent event) -> {
                            Post post = getTableView().getItems().get(getIndex());
                            System.out.println(post.getId() + post.getDescription() + post.getDate());

                            String appId = "232528662540085";
                            String appSecret = "60988e9928012f06c205e07717bb4196";
                            String accessTokenString = "EAADTe8xUrzUBO3mGRL9ZBcnXcIL7SCf9TpxvqmlxZC6xOsKJRFN1TZBEwZAw3PipeZB1vOWMiylsWG8iBBOXZCIZAjUcE4ZAFJH275ZCcXeIZANH5ubkyxa0Av4K779KilKsUwbNHvXCMkv4TZCSZBFtNxkkMCb8g6ToFt335CtcUdjYcLVCbF5jTTpeYSbbDkZB8OUOb8dZAYtu0fMCB0HKpOtxZBDluF8";

                            Facebook facebook = new FacebookFactory().getInstance();
                            facebook.setOAuthAppId(appId, appSecret);
                            facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

                            String msg = "New post "
                                    + "\n*** Description: "
                                    + post.getDescription()
                                    + "\n***Date: "
                                    + post.getDate() ;

                            try {
                                facebook.postStatusMessage(msg);
                                System.out.println("Post shared successfully.");
                            } catch (FacebookException e) {
                                throw new RuntimeException(e);
                            }

                        //    RouterController.navigate("/fxml/ModifyPost.fxml", post.getId());
                        });

                        qrButton.setOnAction((ActionEvent event) -> {
                            Post post = getTableView().getItems().get(getIndex());
                            System.out.println(post.getId());
                            //qrCodeImgModal.setVisible(true);

                            String text = "Post ID: " + post.getId()
                                    + "\nPost Description: " + post.getDescription() + "\nPost date: "
                                    + post.getDate();
                            // Créer un objet QRCodeWriter pour générer le QR code
                            QRCodeWriter qrCodeWriter = new QRCodeWriter();
                            // Générer la matrice de bits du QR code à partir du texte saisi
                            BitMatrix bitMatrix;
                            try {
                                bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                                // Convertir la matrice de bits en image BufferedImage
                                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                                // Enregistrer l'image en format PNG
                                // File outputFile = new File("qrcode.png");
                                // ImageIO.write(bufferedImage, "png", outputFile);
                                // Afficher l'image dans l'interface utilisateur

                                //ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");
                                qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                                //HBox qrCodeImgModel = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModel");
                                qrCodeImgModal.setVisible(true);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        });

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
                            HBox buttons = new HBox();
                            buttons.getChildren().addAll(modifyButton, deleteButton, shareButton, qrButton);
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

    @FXML
    void close_QrCodeModal(MouseEvent event) {
        qrCodeImgModal.setVisible(false);
    }

    public void goToActualite(ActionEvent event) {
            RouterController.navigate("/fxml/PostCrud.fxml");
    }

    public void goToComments(ActionEvent event) {
        RouterController.navigate("/fxml/Commcrud.fxml");
    }
}
