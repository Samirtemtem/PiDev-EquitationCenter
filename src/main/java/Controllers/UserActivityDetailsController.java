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
import Entities.ActivitySession;
import Entities.User;
import Entities.UserActivity;
import Service.ServiceActivity;
import Service.ServiceActivitySession;
import Service.UserActivityService;
import javafx.event.ActionEvent;
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

import javax.swing.*;

public class UserActivityDetailsController implements Initializable {

    @FXML
    private Text category;

    @FXML
    private VBox content_area;

    @FXML
    private Text description;

    @FXML
    private ImageView img;
    public static Activity activity_static;
    public Activity activity;
    @FXML
    private Text offre;
    @FXML
    private Text sessions;
    @FXML
    private HBox offreRow;

    @FXML
    private HBox addNewReviewBtn;

    @FXML
    private HBox rangeTotal1;

    @FXML
    private HBox rangeTotal2;

    @FXML
    private HBox rangeTotal3;

    @FXML
    private HBox rangeTotal4;

    @FXML
    private HBox rangeTotal5;


    @FXML
    private Text price;

    @FXML
    private Text type;

    @FXML
    private Text title;


    @FXML
    private ImageView favBtn;

    private User user = GuiLoginController.user;

    private int found = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        // set product details
        ServiceActivity activityService = new ServiceActivity();
        activity = UserActivityDetailsController.activity_static;
        // System.out.println(produit);


        title.setText(activity.getTitle());

        if (activity.getImageData() != null) {
            Image img = new Image(new ByteArrayInputStream(activity.getImageData()));
            this.img.setImage(img);
        }

        String type = activity.getTypeActivity().toString();
        this.type.setText(type);



        description.setText(activity.getDescription());
        loadActivitySessions();

    }

    private void loadActivitySessions() {
        if (activity != null) {
            ServiceActivitySession serviceActivitySession=new ServiceActivitySession();
            List<ActivitySession> activitySessions = serviceActivitySession.readAllActivitySessions(activity.getId());
            StringBuilder sessionsText = new StringBuilder();

            for (ActivitySession session : activitySessions) {
                sessionsText.append(session.getWeekdayAsString())
                        .append(", De ").append(session.getStartTime())
                        .append(" à ").append(session.getEndTime())
                        .append("\n");
            }
            System.out.println(sessionsText);
            System.out.println(activitySessions);

            sessions.setText(sessionsText.toString());
        }
    }
    void partageFacebook(MouseEvent event) throws SQLException {
}

    public void SubscribetoActivity(MouseEvent mouseEvent) throws SQLException {
            int userid=GuiLoginController.user.getId();
            if(userid==0)
                userid=1;

            UserActivityService sas=new UserActivityService();
            int found = sas.findbyuserIdandActivityId(userid,activity.getId());
            if(found==1)
            {
                System.out.println("Activityuesr already found");
                showInformationAlert("Vous etes déjà abonnée a cette activitée, Veuillez trouver une autre activité", "Erreur");
                return;
            }
            UserActivity userActivity=new UserActivity();
            userActivity.setUserId(userid);
            userActivity.setActivityId(activity.getId());
            userActivity.setFeedback("");
            userActivity.setStars(0);
            System.out.println(userActivity);
            sas.add(userActivity);
            System.out.println("User activity Added successfully");
            showInformationAlert("Vous etes maintenant abonnée a cette activitée, tu dois payer vers notre centre d'equitation", "Information");
            RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
    }
    public void showInformationAlert(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
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
    @FXML
    public void addToFavoriteList(MouseEvent event)
    {

    }
}
