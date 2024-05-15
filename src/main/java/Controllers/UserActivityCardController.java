package Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserActivityCardController {

    @FXML
    private ImageView img;

    @FXML
    private Text name;

    @FXML
    private Text description;

    @FXML
    private Text type;

    @FXML
    private Text price;

    @FXML
    private HBox Details;

    @FXML
    private HBox openDetails;


    public void setActivityData(Activity activity) {
        // Instancier le service de produit
        ServiceActivity serviceActivity = new ServiceActivity();

        if (activity.getImageData() != null) {
            Image img = new Image(new ByteArrayInputStream(activity.getImageData()));
            this.img.setImage(img);
        }

        name.setText(activity.getTitle());

        description.setText("" + activity.getDescription());
        type.setText(""+activity.getTypeActivity());
        price.setText("" + activity.getPrice());

        // tester si un produit a un offre

        // add product to cart btn
        /*
        openDetails.setOnMouseClicked(event -> {
            System.out.println("Activity Details Opening : " + activity.getId());
            User user = new User();
            UserService userService = new UserService();

            if (UserSession.getInstance().getEmail() == null) {

                try {
                    user = userService.getOneUser("nabilkdp0@gmail.com");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(user.getId());

            } else {
                try {
                    user = userService.getOneUser(UserSession.getInstance().getEmail());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(user.getId());

            }
            CommandsService commandsService = new CommandsService();
            Commands command = new Commands();
            command = commandsService.getOneCommand(user.getId());
            System.out.println("Command is :" + command);
            if (command == null) {

                commandsService.addNewCommands(user.getId());
            }

            command = commandsService.getOneCommand(user.getId());

            // System.out.println("Command not null :" + command);
            // System.out.println("Command id :" + command.getId());
            // System.out.println("produit id :" + produit.getId());
            // System.out.println("user id :" + user.getId());

            CommandsProduitService commandeProduitService = new CommandsProduitService();
            // si le produit n'existe pas dans la commande on ajoute ce produit
            if (commandeProduitService.getOneCommandProduit(produit.getId(), command.getId()) == null) {
                commandeProduitService.addNewCommandsProduit(command.getId(), produit.getId());

                Text addedCartModelText = (Text) ((Node) event.getSource()).getScene().lookup("#addedCartModelText");
                addedCartModelText.setText("Product Added To Cart Successfully");

                HBox addedCartModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addedCartModel");
                addedCartModel.setVisible(true);

            } else { // produit existe deja dans la commande => msg affiché
                Text addedCartModelText = (Text) ((Node) event.getSource()).getScene().lookup("#addedCartModelText");
                addedCartModelText.setText("Product is ALready Added To Cart");

                HBox addedCartModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addedCartModel");
                addedCartModel.setVisible(true);

            }
            // System.out.println(commandsService.getOneCommand(user.getId()));

        });
        // END add product to cart cart
*/
        // Open Product Details
        openDetails.setOnMouseClicked(event -> {
            System.out.println("ID du Activité à afficher les details : " + activity.getId());
           // Collecte.setIdProduit(produit.getId());
//##### STATIC IDEA TO PASS ACTIVITYID####)
            UserActivityDetailsController.activity_static=activity;
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Client/UserActivityDetails.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis ce controller
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de AddProduct.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // END - Open Product Details
    }

}
