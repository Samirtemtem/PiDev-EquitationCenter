package testconnection;

import Controllers.LoadingscreenController;
import Controllers.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RouterController.setPrimaryStage(primaryStage); // Set the primary stage
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/PostCrud.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/loadingscreen.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("../assets/logo.png"));
        primaryStage.getIcons().add(icon);

        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Get the LoadingscreenController instance
        LoadingscreenController controller = loader.getController();

        // Call the method to play the logo animation
        controller.initialize();

       */
    }

    public static void main(String[] args) {
        launch(args);
    }
}