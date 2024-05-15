package Controllers;

import Entities.Product;
import Service.ServiceProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyProduit implements Initializable, InitializableController {

    private int ProductID;
    private FileChooser fileChooser = new FileChooser();
    private byte[] imageBlob;

    @FXML
    private TextField Name;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField Price;

    @FXML
    private TextField StockQty;

    @FXML
    private TextArea Description;

    @FXML
    private ServiceProduct serviceProduit = new ServiceProduct();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Product produit = serviceProduit.findById(ProductID);
            System.out.println(ProductID);
            if (produit != null) {
                Name.setText(produit.getName());
                Description.setText(produit.getDescription());
                StockQty.setText(String.valueOf(produit.getStockQty()));
                Price.setText(String.valueOf(produit.getPrice()));
            } else {
                // Handle if produit is not found
                System.out.println("Produit non trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        configureFileChooser();
    }

    private void configureFileChooser() {
        // Configure the file chooser to accept only image files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    @FXML
    public void chooseImage(ActionEvent event) {
        // Show the file chooser dialog to select an image file
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Load the selected image into the ImageView
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                // Convert the image to a byte array for storage
                imageBlob = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the image reading error
            }
        }
    }

    private void loadData() {
        try {
            Product product = serviceProduit.findById(this.ProductID);
            System.out.println(product.toString());
            if (product != null) {
                Name.setText(product.getName());
                Description.setText(product.getDescription());
                StockQty.setText(String.valueOf(product.getStockQty()));
                Price.setText(String.valueOf(product.getPrice()));
                imageBlob = product.getBlobImage();
            } else {
                // Handle if produit is not found
                System.out.println("Produit non trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur");
        }
    }

    @FXML
    void modifierProduct(ActionEvent event) {
        try {
            // Contrôles de saisie
            if (Name.getText().isEmpty()) {
                showErrorMessage("Product Name is required");
                return;
            }

            if (Price.getText().isEmpty()) {
                showErrorMessage("Price is required");
                return;
            }

            if (StockQty.getText().isEmpty()) {
                showErrorMessage("Stock Quantity is required");
                return;
            }

            String name = Name.getText();
            String description = Description.getText();
            double price = Double.parseDouble(Price.getText());
            int stockQty = Integer.parseInt(StockQty.getText());

            serviceProduit.update(new Product(this.ProductID, name, description, price, stockQty, imageBlob));

            showSuccessMessage("Produit modifié avec succès");

        } catch (NumberFormatException e) {
            showErrorMessage("Invalid numeric input. Please enter valid numbers.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    router.navigate("/fxml/Admin/ProductCRUD.fxml");
                    System.out.println("Button Clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void returnTo(MouseEvent mouseEvent) {
        System.out.println("RETURN TO EXECUTED");
        RouterController router = new RouterController();
        router.navigate("/fxml/Admin/ProductCRUD.fxml");
    }

    public void modifierUtilisateur(ActionEvent actionEvent) {
    }

    @Override
    public void init(Integer Id) {
        System.out.println("Produit ID from ModifyProduitController:" + ProductID);
        this.ProductID = Id;
        loadData();
    }
}