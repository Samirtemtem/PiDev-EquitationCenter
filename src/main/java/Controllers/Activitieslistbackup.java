package Controllers;

import Entities.Activity;
import Service.ServiceActivity;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Activitieslistbackup implements Initializable {
    public GridPane ActivitiesListContainer;
    public Pane content_area;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("I GOT HERE");
        // Call your service to get the list of events
        ServiceActivity es=new ServiceActivity();
        List<Activity> activities;
        try {
            activities = es.ReadAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Populate the GridPane with the events data
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < activities.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/Client/UserActivityCard.fxml"));
                VBox oneActivityCard = fxmlLoader.load();
                UserActivityCardController ActivityCardController = fxmlLoader.getController();
                ActivityCardController.setActivityData(activities.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                ActivitiesListContainer.add(oneActivityCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneActivityCard, new Insets(0, 10, 10, 5));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
