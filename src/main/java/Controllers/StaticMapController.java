package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticMapController {

    @FXML
    private ImageView mapImage;

    public void initialize() {
        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=36.9089863,10.0863061&zoom=13&size=600x300&maptype=roadmap&markers=color:red%7c40.712776,-74.005974&key=AIzaSyAgpCwkkTAaRSVoP6_UfKM7-IFspmpOTuA";

        Image image = new Image(mapUrl);
        mapImage.setImage(image);
    }
}
