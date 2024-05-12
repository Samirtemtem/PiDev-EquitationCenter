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
        String name="Bienvenue, "+guilogin.user.getLastName()+"!";
        adminNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/Login.fxml");
    }

    public void goToNavigate(ActionEvent actionEvent) {
       // RouterController.navigate("/fxml/Client/ClientDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {
            RouterController router=new RouterController();
            router.navigate("../fxml/Admin/UsersCrud.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/Activities/ActivitiesCRUD.fxml");
    }



    public void GoToActivitySessions(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/ActivitySession/ActivitySessionCRUD.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/ProductCRUD.fxml");
    }

    public void goToArticles(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/PostCrud.fxml");
    }

    public void goToReclamations(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Admin/CrudComplaints.fxml");
    }
}
