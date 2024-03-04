package Controllers;

import Entities.Activity;
import Entities.Product;
import Service.ServiceActivity;
import Service.ServiceProduct;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductDisplayController {

    @FXML
    private VBox productBox;

    private ServiceProduct serviceProduct = new ServiceProduct();

    public void initialize() {
        try {
            List<Product> products = serviceProduct.ReadAll();
            for (Product product : products) {
                HBox hbox = createProductBox(product);
                productBox.getChildren().add(hbox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private HBox createProductBox(Product product) {
        HBox hbox = new HBox(10);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        // Convert byte[] to JavaFX Image
        try {
            Image image = createImageFromBytes(product.getBlobImage());
            imageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle image conversion error
        }

        Label titleLabel = new Label(product.getName());
        titleLabel.getStyleClass().add("activity-title");

        Label descriptionLabel = new Label(product.getDescription());
        descriptionLabel.getStyleClass().add("activity-description");
        Button plusInfosButton = new Button("Plus Infos");
        plusInfosButton.setOnAction(event -> {
            try {
                openProductDetails(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Add more labels or UI elements for other activity details if needed

        hbox.getChildren().addAll(imageView, titleLabel, descriptionLabel,plusInfosButton);
        return hbox;
    }

    private Image createImageFromBytes(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return new Image(bis);
    }

    public void close() {
        try {
            serviceProduct.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void openProductDetails(Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/ProductDetails.fxml"));
        Parent root = loader.load();

        ProductDetails controller = loader.getController();
        controller.initData(product);

        // Get the current stage from any UI element
        Stage stage = (Stage) productBox.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Details Produits");
        stage.show();
    }
}
