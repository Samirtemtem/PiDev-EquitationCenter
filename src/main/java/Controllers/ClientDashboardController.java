package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
public class ClientDashboardController {


        public void initialize() {

        }
        public void goToLogn(MouseEvent mouseEvent) {
            RouterController.navigate("/fxml/Login/Login.fxml");
        }

        public void goToNavigate(ActionEvent actionEvent) {
            RouterController.navigate("/fxml/Admin/AdminDashboard.fxml");
        }

        public void goToUsers(MouseEvent mouseEvent) {
        }

        public void goToActivities(MouseEvent mouseEvent) {
            RouterController.navigate("../fxml/Activities/ActivitiesCRUD.fxml");
        }



        public void GoToActivitySessions(MouseEvent mouseEvent) {
            RouterController.navigate("../fxml/ActivitySession/ActivitySessionCRUD.fxml");
        }
    }

