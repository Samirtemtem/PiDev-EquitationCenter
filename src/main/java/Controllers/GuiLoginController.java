/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Service.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nl.captcha.Captcha;
import javafx.embed.swing.SwingFXUtils;

/**
 * FXML Controller class
 *
 * @author medmo
 */
public class GuiLoginController implements Initializable {
    public static User user = new User();

    @FXML
    private ImageView btnReturn;
    @FXML
    private AnchorPane bord;
    @FXML
    private Button btnLogin;
    @FXML
    private Label erreur;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField emailInput;
@FXML
    private TextField captchaField;
    @FXML
    private ImageView captchaImg;

    Captcha captcha;
    Boolean captchaTest = true;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.setOnMouseEntered(event -> {
            btnLogin.setEffect(new DropShadow());
        });

        btnLogin.setOnMouseExited(event -> {
            btnLogin.setEffect(null);
        });
        captcha = setCaptcha();

    }

    @FXML
    private void returnTo(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AdminDashboard.fxml"));
        try {
            Parent root = loader.load();
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
        ServiceUser su = new ServiceUser();

        String email = emailInput.getText();
        String password = passwordInput.getText();

        user = su.login(email, password);
        if (captcha.isCorrect(captchaField.getText())) {
            captchaTest = true;

            user = su.login(email, password);
            if (user == null){
                erreur.setText("Email ou mot de passe incorrecte");
            }
            else{
                if (user.getRole().equals(User.Role.ADMIN))
                    RouterController.navigate("../fxml/admin/AdminDashboard.fxml");
                if (user.getRole().equals(User.Role.CLIENT))
                    RouterController.navigate("../fxml/Client/ClientDashboard.fxml");
                if (user.getRole().equals(User.Role.VET))
                    RouterController.navigate("../fxml/Vet/VetDashboard.fxml");
                if (user.getRole().equals(User.Role.INSTRUCTOR))
                    System.out.println("Instructor dashboard is not integrated yet");


            }
        } else {
            captchaTest = false;
            captcha = setCaptcha();
            erreur.setText("captcha invalid !");

        }
    }




    public void goTo(String view){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view+".fxml"));
        try {
            Parent root = loader.load();
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public Captcha setCaptcha() {
        Captcha captchaV = new Captcha.Builder(250, 150)
                .addText()
                .addBackground()
                .addNoise()
                // .gimp()
                .addBorder()
                .build();

        // System.out.println(captchaV.getImage());
        WritableImage image = SwingFXUtils.toFXImage(captchaV.getImage(), null);

        captchaImg.setImage(image);



        return captchaV;
    }

}
