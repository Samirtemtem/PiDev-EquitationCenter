package Controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private AnchorPane bord;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        RouterController.navigate("/fxml/Login/Login.fxml");
    }

    @FXML
    private void goToSignup(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/signup.fxml"));
        try {
            Parent root = loader.load();
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void OpenMap(MouseEvent mouseEvent) {
            Stage stage = new Stage ();

            final WebView webView = new WebView();
            final WebEngine webEngine = webView.getEngine();
            webEngine.load(getClass().getResource("/googleMaps.html").toString());

            // create scene
            //   stage.getIcons().add(new Image("/Images/logo.png"));
            stage.setTitle("localisation");
            Scene scene = new Scene(webView,1000,700, Color.web("#666970"));
            stage.setScene(scene);
            // show stage
            stage.show();
        }
        static { // use system proxy settings when standalone application
            System.setProperty("java.net.useSystemProxies", "true");
        }

    public void goToCalendrier(MouseEvent mouseEvent) {
        RouterController.navigateCalendrier("../fxml/Activities/calendar.fxml");
    }

    //RouterController.navigate("/fxml/Client/static_map.fxml");
}
