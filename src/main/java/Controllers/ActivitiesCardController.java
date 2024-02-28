package Controllers;

import Entities.Activity;
import Entities.ActivitySession;
import Service.ServiceActivitySession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ActivitiesCardController {

    @FXML
    private HBox activitiesCard;

    @FXML
    private ImageView image;

    @FXML
    private Text Title;

    @FXML
    private Text Weekdays;

    @FXML
    private Text Price;

    @FXML
    private Button reservebtn;

    // Method to set attributes of the ActivitiesCard based on an Activity object
    public void setActivity(Activity activity) {
        // Set image
        if (activity.getImageData() != null) {
            Image img = new Image(new ByteArrayInputStream(activity.getImageData()));
            image.setImage(img);
        } else {
            // Set default image or placeholder if no image data
            // Example:
            // Image img = new Image(getClass().getResourceAsStream("/path/to/default_image.png"));
            // image.setImage(img);
        }

        // Set title
        Title.setText(activity.getTitle());
        ServiceActivitySession serviceActivitySession = new ServiceActivitySession();
        List<ActivitySession> activitySessions = serviceActivitySession.readAllActivitySessions(activity.getId());
        String weekdaysText = formatWeekdays(activitySessions);
        Weekdays.setText(weekdaysText);
        // Set price
        Price.setText(String.valueOf(activity.getPrice()));

        // You can add more attributes if needed, such as description, buttons, etc.
    }

    private String formatWeekdays(List<ActivitySession> activitySessions) {
        StringBuilder weekdaysBuilder = new StringBuilder();
        for (ActivitySession session : activitySessions) {
            String weekday = getWeekdayAsString(session.getWeekday());
            weekdaysBuilder.append(weekday).append(", ");
        }
        // Remove the last comma and space
        if (weekdaysBuilder.length() > 2) {
            weekdaysBuilder.setLength(weekdaysBuilder.length() - 2);
        }
        return weekdaysBuilder.toString();
    }

    private String getWeekdayAsString(int weekday) {
        switch (weekday) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Unknown";
        }
    }
    // Additional methods for handling button actions, if needed
    @FXML
    private void handleReserveButtonAction() {
        // Add action for reserve button, if needed
    }

    // You can add more methods for handling other actions, if needed
}
