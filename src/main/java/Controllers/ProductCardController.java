package Controllers;

import Entities.Activity;
import Entities.ActivitySession;
import Entities.Product;
import Service.ServiceActivitySession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ProductCardController {

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
    public void setProduct(Product product) {
        // Set image
        if (product.getBlobImage() != null) {
            Image img = new Image(new ByteArrayInputStream(product.getBlobImage()));
            image.setImage(img);
        } else {
            // Set default image or placeholder if no image data
            // Example:
            // Image img = new Image(getClass().getResourceAsStream("/path/to/default_image.png"));
            // image.setImage(img);
        }

        // Set title
        Title.setText(product.getName());
        // Set price
        Price.setText(String.valueOf(product.getPrice()));

        // You can add more attributes if needed, such as description, buttons, etc.
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

    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/login.fxml");
    }
}
