package Controllers;

import Entities.*;
import Service.ServiceActivitySession;
import Service.ServiceProductOrder;
import Service.UserActivityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductDetails {

    public Label activitySessionsLabel;
    @FXML
    private ImageView activityImage;

    @FXML
    private Label activityName;

    @FXML
    private Label activityDescription;

    @FXML
    private Label activityPrice;

    @FXML
    private Button subscribeButton; // New Button for Subscription

    private Product product;
    public void initialize() {

        // Populate the activity details
        if (product != null) {
            activityName.setText("Nom: "+product.getName());
            activityDescription.setText("Description: "+product.getDescription());
            activityPrice.setText("Prix: " + product.getPrice());

            try {
                Image image = createImageFromBytes(product.getBlobImage());
                activityImage.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle image conversion error
            }
        }

    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private Image createImageFromBytes(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return new Image(bis);
    }

    @FXML
    private void goBack() {
        // Code to go back to the previous interface
        // You can use the FXMLLoader to load the previous FXML
    }


    public void goToNavigate(ActionEvent actionEvent) {
    }

    public void goToActivities(MouseEvent mouseEvent) {
    }

    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/Login.fxml");

    }

    public void initData(Product product) {
        this.product=product;
        initialize();
    }

    public void SubscribeToActivity(ActionEvent actionEvent) throws SQLException {
        if(product.getStockQty()<1) {
            showErrorAlert("Stock epuisé", "Erreur stock");
            return;
        }
        RouterController.navigatepay("/fxml/Client/GuiPaiement.fxml", this.product);
    } /*  int userid=GuiLoginController.user.getId();
        if(userid==0)
            userid=1;
*/
       /* ProductOrder productOrder=new ProductOrder();
        ServiceProductOrder spo=new ServiceProductOrder();
        productOrder.setId_client(userid);
        productOrder.setProduct_id(product.getId());
        productOrder.setPrice(product.getPrice());
        productOrder.setTotal_price(productOrder.getPrice());
        productOrder.setStatus("Commande en cours de traiement");
        System.out.println(product);
        spo.add(productOrder);
        System.out.println("Product Order Added successfully");
        showInformationAlert("Commande passée", "Information");
*/
    public void showInformationAlert(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public void showErrorAlert(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
