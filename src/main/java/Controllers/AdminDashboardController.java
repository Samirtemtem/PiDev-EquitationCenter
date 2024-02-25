package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        GuiLoginController guilogin = new GuiLoginController();
        String name="Bienvenue "+guilogin.user.getName()+"!";
        adminNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/AdminLogin.fxml");
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
