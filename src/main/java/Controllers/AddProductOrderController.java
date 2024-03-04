package Controllers;

import Entities.User;
import Entities.Product;
import Entities.ProductOrder;
import Service.ServiceUser;
import Service.ServiceProduct;
import Service.ServiceProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class AddProductOrderController {

    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private ComboBox<User> ClientcomboBox;
    @FXML
    private TextField qty;
    @FXML
    private TextField total_Price;
    @FXML
    private TextField status;
    @FXML
    private TextField Price;

    private ServiceProductOrder serviceProductOrder = new ServiceProductOrder();
    private ObservableList<User> clientList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            ServiceProduct spo = new ServiceProduct();
            productList.addAll(spo.ReadAll());

            ServiceUser spoo = new ServiceUser();
            clientList.addAll(spoo.ReadClients());

            // Initialize ComboBoxes
            productComboBox.setItems(productList);
            ClientcomboBox.setItems(clientList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addProductOrder(ActionEvent actionEvent) {
        try {


            // Contrôle de saisie pour qty
            if (qty.getText().isEmpty() || qty.getText().length() > 8) {
                showErrorMessage("Quantity is required and should be at most 8 digits");
                return;
            }
            int Qty = Integer.parseInt(qty.getText());

            // Contrôle de saisie pour status
            String Status = status.getText();
            if (Status.isEmpty()) {
                showErrorMessage("Status is required");
                return;
            }

            // Contrôle de saisie pour Price
            if (Price.getText().isEmpty() || Price.getText().length() > 8) {
                showErrorMessage("Price is required and should be at most 8 digits");
                return;
            }
            float productPrice = Float.parseFloat(Price.getText());

            float total_price = productPrice * Qty;
            int selectedid_client = ClientcomboBox.getValue().getId();

            // Création d'un objet ProductOrder
            String query = "INSERT INTO product_order (Price, Qty, Status, Product_id, Total_price,id_client) VALUES (?, ?, ?, ?, ?)";
            int selected_product=productComboBox.getValue().getId();
            Product p=productComboBox.getValue();
            System.out.println(p.getStockQty());
            if (p.getStockQty()-Qty<0)
            {
                showErrorMessage("T'as pas un stock suffisant pour cette produit");
                return;
            }
            p.setStockQty(p.getStockQty()-Qty);

            ServiceProduct sp=new ServiceProduct();
            sp.update(p);
            ProductOrder productOrder = new ProductOrder(productPrice, Qty, Status, selected_product,selectedid_client,total_price);
            serviceProductOrder.add(productOrder);
            showSuccessMessage("Product Order added successfully");

        } catch (NumberFormatException e) {
            // Gestion des erreurs de conversion numérique
            showErrorMessage("Invalid numeric input. Please enter valid numbers.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error adding Product Order");
        }
    }

    @FXML
    private void returnTo(MouseEvent mouseEvent) {
        System.out.println("Button Clicked");
        RouterController router = new RouterController();
        router.navigate("../fxml/ProductOrdreCRUD.fxml");
        System.out.println("Button Clicked");
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
    }
}