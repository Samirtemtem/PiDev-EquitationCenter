package Controllers;

import Entities.Product;
import Service.ServiceProduct;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class AddProduitController {

    @FXML
    private TextField Id;

    @FXML
    private TextField Nom;
    @FXML
    private ImageView imageView;

    @FXML
    private TextField Price;

    @FXML
    private TextField StockQty;

    @FXML
    private TextArea Description;

    private ServiceProduct serviceProduit = new ServiceProduct();

    // Add a FileChooser for selecting the image file
    private FileChooser fileChooser = new FileChooser();

    // Add a byte array to store the image blob
    private byte[] imageBlob;
    @FXML
    public void initialize() {
        // ... existing initialization code
        configureFileChooser();
    }

    

        @FXML
        public void AddProduit(ActionEvent actionEvent) {
            try {
                // Contrôle de saisie pour le nom du produit
                if (Nom.getText().isEmpty()) {
                    showErrorMessage("Product Name is required");
                    return;
                }

                String productName = Nom.getText();

                // Contrôle de saisie pour le prix
                if (Price.getText().isEmpty()) {
                    showErrorMessage("Price is required");
                    return;
                }
                float price = Float.parseFloat(Price.getText());

                // Contrôle de saisie pour la quantité en stock
                if (StockQty.getText().isEmpty()) {
                    showErrorMessage("Stock Quantity is required");
                    return;
                }
                int stockQty = Integer.parseInt(StockQty.getText());

                String description = Description.getText();

                Product product = new Product(productName, description, price, stockQty, imageBlob);

                serviceProduit.add(product);
                showSuccessMessage("Produit ajouté avec succès");

                MouseEvent e = null;
                returnTo(e);
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid numeric input. Please enter valid numbers.");
            } catch (SQLException e) {
                e.printStackTrace();
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




    @FXML
    private ImageView btnReturn;

    public void returnTo(MouseEvent mouseEvent) {
        RouterController router = new RouterController();
        router.navigate("/fxml/Admin/ProductCRUD.fxml");
        System.out.println("Button Clicked");
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
                RouterController router = new RouterController();
                router.navigate("/fxml/Admin/ProductCRUD.fxml");
                System.out.println("Button Clicked");
            }
        });
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



}
