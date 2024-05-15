package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class ActivitiesListController {
    public void initialize() {

    }
    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/Login.fxml");
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/Client/ClientDashboard.fxml");
    }



    public void GoToActivitySessions(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/ActivitySession/ActivitySessionCRUD.fxml");
    }
}
