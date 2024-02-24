package Controllers;


import Entities.User;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;

public class ImageViewTableCellFactory<T> implements Callback<TableColumn<User, T>, TableCell<User, T>> {

    @Override
    public TableCell<User, T> call(TableColumn<User, T> param) {
        return new TableCell<User, T>() {
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
                    User rowData = (User) getTableRow().getItem();
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