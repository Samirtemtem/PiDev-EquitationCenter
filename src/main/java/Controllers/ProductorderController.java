package Controllers;

import Entities.Product;
import Entities.ProductOrder;
import Service.ServiceProduct;
import Service.ServiceProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductorderController {

    @FXML
    private TableView<ProductOrder> tableView;

    private final ServiceProductOrder productServiceOrdre = new ServiceProductOrder();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateProductOrderList();
    }




    public void updateProductOrderList() {
        try {
            List<ProductOrder> productOrders= productServiceOrdre.ReadAll();
            System.out.println(productOrders);
            tableView.getItems().clear();
            tableView.getItems().addAll(productOrders);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<ProductOrder, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProductOrder, Integer> produit_idColumn = new TableColumn<>("product_id");
        produit_idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

        TableColumn<ProductOrder, Float> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ProductOrder, String> Total_priceColumn = new TableColumn<>("Prix_total");
        Total_priceColumn.setCellValueFactory(new PropertyValueFactory<>("Total_price"));

        TableColumn<ProductOrder, Integer> stockQtyColumn = new TableColumn<>("Quantité");
        stockQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<ProductOrder, Void> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ProductOrder, Void> id_clientColumn = new TableColumn<>("id_client");
        id_clientColumn.setCellValueFactory(new PropertyValueFactory<>("id_client"));

        TableColumn<ProductOrder, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        tableView.getColumns().addAll(idColumn, priceColumn, Total_priceColumn, statusColumn, stockQtyColumn, produit_idColumn, actionColumn,id_clientColumn);
    }


    private Callback<TableColumn<ProductOrder, Void>, TableCell<ProductOrder, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<ProductOrder, Void>, TableCell<ProductOrder, Void>>() {
            public TableCell<ProductOrder, Void> call(final TableColumn<ProductOrder, Void> param) {
                final TableCell<ProductOrder, Void> cell = new TableCell<ProductOrder, Void>() {
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
                            ProductOrder ProductOrder = getTableView().getItems().get(getIndex());
                            System.out.println(ProductOrder.Id);
                            RouterController.navigate("/fxml/Admin/ModifyProductOrder.fxml", ProductOrder.Id);
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            ProductOrder ProductOrder = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Activity");
                            alert.setContentText("Vous etes sur tu veux supprimer cette activité?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServiceProductOrder r=new ServiceProductOrder();
                                    r.delete(ProductOrder);

                                    updateProductOrderList();
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
        router.navigate("/fxml/Admin/AddProductOrder.fxml");
    }
    @FXML
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/Admin/AdminDashboard.fxml");
    }
}
