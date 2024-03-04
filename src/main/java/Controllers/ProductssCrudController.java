package Controllers;

import Entities.Product;
import Service.ServiceProduct;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductssCrudController {

    @FXML
    private TableView<Product> tableView;

    private final ServiceProduct productService = new ServiceProduct();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateProductList();
    }

    public void updateProductList() {
        try {
            List<Product> products = productService.ReadAll();
            tableView.getItems().clear();
            tableView.getItems().addAll(products);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Integer> stockQtyColumn = new TableColumn<>("StockQty");
        stockQtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockQty"));

        TableColumn<Product, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        TableColumn<Product, ImageView> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(param -> {
            Product product = param.getValue();
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(product.getBlobImage())));
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            return new SimpleObjectProperty<>(imageView);
        });

        tableView.getColumns().addAll(idColumn, nameColumn, priceColumn, descriptionColumn, stockQtyColumn, imageColumn, actionColumn);
    }

    private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
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
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println(product.id);
                            RouterController.navigate("/fxml/Admin/ModifyProduit.fxml", product.id);
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Activity");
                            alert.setContentText("Vous etes sur tu veux supprimer cette activité?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServiceProduct r=new ServiceProduct();
                                    r.delete(product);

                                    updateProductList();
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

    @FXML
    public void searchquery(KeyEvent keyEvent) {
        // Implement search functionality
    }

    @FXML
    public void gotoAjouter(ActionEvent actionEvent) {

        RouterController router=new RouterController();
        router.navigate("/fxml/Admin/AddProduit.fxml");
    }
    @FXML
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/Admin/AdminDashboard.fxml");
    }

    public void goToCommandes(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/ProductOrdreCRUD.fxml");
    }
}
