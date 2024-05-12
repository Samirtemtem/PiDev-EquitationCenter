package Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Entities.Activity;
import Entities.Users;
import Service.ServiceActivity;
import Service.ServiceProduct;
import Entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;

import javafx.util.Duration;

public class UserProductDetailsCardController implements Initializable {



    @FXML
    private Text description;

    @FXML
    private ImageView img;
    public static Product product_static;
    public Product product;


    @FXML
    private Text Price;



    @FXML
    private Text stock;



    private Users user = GuiLoginController.user;

    private int found = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        // set product details
        ServiceProduct serviceProduct = new ServiceProduct();
        product = UserProductCardController.product_static;
        // System.out.println(produit);





        if (product. getBlobImage() != null) {
            Image img = new Image(new ByteArrayInputStream(product.getBlobImage()));
            this.img.setImage(img);
        }
        Price.setText("Nom Produit: "+product.getName()+","+String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        // get category Name

        float prixApresOffre = 0;



    }
    public void commande(MouseEvent mouseEvent) {
        RouterController.navigatepay("/fxml/Client/GuiPaiement.fxml",product);
    }

    //@FXML
    //  void partageFacebook(MouseEvent event) throws SQLException {
        /*Collecte produit = new Collecte();
        ProduitService produitService = new ProduitService();
        produit = produitService.getOneProduct(Collecte.getIdProduit());

        String appId = "232528662540085";
        String appSecret = "60988e9928012f06c205e07717bb4196";
        String accessTokenString = "EAADTe8xUrzUBALc1rb6aaElV1pappD7JSyoXACZAK83fZBP6OcsTKxVDvUIR5fq8q7kx5EBPiUiNU6CzWJBFLqxDZAQCmgm4YSlqXYrNsmeAbZBzTTIdYiZBprB0Gl7ubxoZAH4FPs9D5IwhmmHlMutCZCB7fhJcto7V1JtK5v33kgFYeopUIwYl1ZCJHFJqVP1zQdlmh1YJe9RegTMJ3Avu";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

   /*     String msg = "A new product is available on ZeroWaste" + "\n***Product Name: " + produit.getNom_produit()
                + "\n***Product Description: "
                + produit.getDescription()
                + "\n***Product Price: "
                + produit.getPrix_produit() + "\n***Product Points: "
                + produit.getPrix_point_produit();
        try {
            /*facebook.postStatusMessage(msg);
            TrayNotificationAlert.notif("Product", "Product shared successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        } catch (FacebookException e) {
            e.printStackTrace();
            TrayNotificationAlert.notif("Product", "token was changed.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
        }

        }


    }*/

}
