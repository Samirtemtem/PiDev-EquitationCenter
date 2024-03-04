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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyProductOrder implements Initializable,InitializableController  {

    private int Id;

    // Method to receive the ID

    @FXML
    private TextField Price   ;

    @FXML
    private TextField status;

    @FXML
    private TextField qty;
    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private ComboBox<User> clientComboBox;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private ObservableList<User> clientList = FXCollections.observableArrayList();



    @FXML
    private ServiceProductOrder ServiceProductOrder = new ServiceProductOrder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceProduct spo=new ServiceProduct();
            productList.addAll(spo.ReadAll());
            ServiceUser spoo=new ServiceUser();
            clientList.addAll(spoo.ReadClients());
            ProductOrder productOrder = ServiceProductOrder.findById(Id);

            if (productOrder != null) {
                // Get the product ID
                int productId = productOrder.getProduct_id();
                int id_client = productOrder.getId_client();

                // Find the product name corresponding to the product ID
                Product selectedProduct=spo.findById(productId);
                User selectedClient=spoo.findById(id_client);

                // Set the selected product in the ComboBox
                productComboBox.setValue(selectedProduct);
                clientComboBox.setValue(selectedClient);

                // Rest of your initialization code
            } else {
                // Handle if product is not found
                System.out.println("Produit non trouvé.");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productComboBox.setItems(productList);
        clientComboBox.setItems(clientList);
        try {
            ProductOrder ProductOrder = ServiceProductOrder.findById(Id);
            if (ProductOrder != null) {
                int product_id = productComboBox.getValue().getId();
                int id_client = clientComboBox.getValue().getId();
                status.setText(String.valueOf(ProductOrder.getStatus()));
                qty.setText(String.valueOf(Integer.valueOf(ProductOrder.getQty())));
                Price.setText(String.valueOf(ProductOrder.getPrice()));


            } else {
                // Handle if produit is not found
                System.out.println("Produit non trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadData() {
        try {
            ProductOrder productOrder = ServiceProductOrder.findById(Id);
            if (productOrder != null) {
                // ... existing code

                // Load data into the UI components
                status.setText(String.valueOf(productOrder.getStatus()));
                qty.setText(String.valueOf(productOrder.getQty()));
                Price.setText(String.valueOf(productOrder.getPrice()));
            } else {
                // Handle if productOrder is not found
                System.out.println("Product Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    @FXML
    void modifierProductOrder(ActionEvent event) {
        try {
            // ... existing code

            // Contrôles de saisie
            if (productComboBox.getValue() == null) {
                showErrorMessage("Please select a product");
                return;
            }

            if (clientComboBox.getValue() == null) {
                showErrorMessage("Please select a client");
                return;
            }

            if (qty.getText().isEmpty()) {
                showErrorMessage("Quantity is required");
                return;
            }

            if (status.getText().isEmpty()) {
                showErrorMessage("Status is required");
                return;
            }

            if (Price.getText().isEmpty()) {
                showErrorMessage("Price is required");
                return;
            }

            try {
                int productId = productComboBox.getValue().getId();
                System.out.println(productId);
                int id_client = clientComboBox.getValue().getId();
                // use a meaningful variable name
                int qtyValue = Integer.parseInt(qty.getText());
                String statusValue = status.getText();
                float priceValue = Float.parseFloat(Price.getText());
                float totalPrice = priceValue * qtyValue;


                // Assuming Id is the first parameter in the ProductOrder constructor
                ProductOrder updatedProductOrder = new ProductOrder(Id, priceValue, qtyValue, statusValue, productId, totalPrice,id_client);

                ServiceProductOrder.update(updatedProductOrder);

                showSuccessMessage("Produit modifié avec succès");

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, such as showing an error message
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately, such as showing an error message
        }
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


    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okayButton) {
                try {
                    RouterController router = new RouterController();
                    router.navigate("/fxml/Admin/ProductOrdreCRUD.fxml");
                    System.out.println("Button Clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void returnTo(MouseEvent mouseEvent) {
        System.out.println("RETURN TO EXECUTED");
        RouterController router = new RouterController();
        router.navigate("/fxml/Admin/ProductOrdreCRUD.fxml");
    }

    public void modifierUtilisateur(ActionEvent actionEvent) {
    }

    @Override
    public void init(Integer Id) {
        System.out.println("Id from ModifyProductOrder:"+Id);
        this.Id = Id;
        loadData();
    }
}
