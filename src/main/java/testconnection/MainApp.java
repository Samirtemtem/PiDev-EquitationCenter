package testconnection;

import Controllers.LoadingscreenController;
import Controllers.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RouterController.setPrimaryStage(primaryStage);

        // Set the initial scene for the primary stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ActivitiesCRUD.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Image icon = new Image(getClass().getResourceAsStream("../assets/logo.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}