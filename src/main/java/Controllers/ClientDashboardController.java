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
  @FXML
  private Label adminNameLabel;

        public void initialize() {
          GuiLoginController guilogin = new GuiLoginController();
          String name="Bienvenue "+guilogin.user.getLastName() + "!";
          adminNameLabel.setText(name);

        }
        public void goToLogn(MouseEvent mouseEvent) {
            RouterController.navigate("/fxml/Login/Login.fxml");
        }

        public void goToNavigate(ActionEvent actionEvent) {
            RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
        }
    public void goToReclamations(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/Client/Complaint.fxml");
    }
        public void goToCommands(MouseEvent event)
        {
            RouterController.navigate("/fxml/Client/ProductsList.fxml");
        }
        public void goToUsers(MouseEvent mouseEvent) {
        }

        public void goToActivities(MouseEvent mouseEvent) {
            RouterController.navigate("../fxml/Client/ActivitiesList.fxml");
        }

        public void GoToActivitySessions(MouseEvent mouseEvent) {
            RouterController.navigate("../fxml/Client/ActivitiesList.fxml");
        }

    public void goToProfile(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Client/ModifiyProfil.fxml");
    }
}

