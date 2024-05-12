package Controllers;

import Entities.Activity;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;

public class ImageViewTableCellFactory<T> implements Callback<TableColumn<Activity, T>, TableCell<Activity, T>> {

    @Override
    public TableCell<Activity, T> call(TableColumn<Activity, T> param) {
        return new TableCell<Activity, T>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Activity rowData = (Activity) getTableRow().getItem();
                    if (rowData.getImageData() != null) {
                        Image image = new Image(new ByteArrayInputStream(rowData.getImageData()));
                        imageView.setImage(image);
                    } else {
                        imageView.setImage(null);
                    }
                }
            }
        };
    }
}