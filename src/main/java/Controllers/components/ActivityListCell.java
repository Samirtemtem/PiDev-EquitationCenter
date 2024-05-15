package Controllers.components;

import Entities.Activity;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ActivityListCell extends ListCell<Activity> {

    private final ImageView imageView = new ImageView();

    public ActivityListCell() {
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(Activity activity, boolean empty) {
        super.updateItem(activity, empty);

        if (empty || activity == null || activity.getImageData() == null) {
            imageView.setImage(null);
        } else {
            Image image = getImageFromByteArray(activity.getImageData());
            imageView.setImage(image);
        }
    }

    private Image getImageFromByteArray(byte[] imageData) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
