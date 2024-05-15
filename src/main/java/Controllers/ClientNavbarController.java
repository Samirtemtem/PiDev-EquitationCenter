package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class ClientNavbarController {

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
    }


    public void goToActivites(ActionEvent mouseEvent) {
        RouterController.navigate("../fxml/Client/ActivitiesList.fxml");
    }



    public void goToProducts(ActionEvent mouseEvent) {
        RouterController.navigate("../fxml/Client/ProductsList.fxml");
    }
}
