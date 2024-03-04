package Controllers;

import Entities.Post;
import Service.ServicePost;
import com.google.protobuf.BoolValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.time.LocalDate;

import java.sql.SQLException;


public class ModifyPostController implements InitializableController {

    private int id;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextArea descriptionField;

    private ServicePost servicePost = new ServicePost();

    public void setId(int id) {
        this.id = id;
    }

    public void init(int id) {
        this.id = id;
    }

    private void loadData() {
        try {
            Post post = servicePost.findById(id);
            if (post != null) {
                this.descriptionField.setText(post.getDescription());

                java.util.Date date = post.getDate();

                this.dateField.setValue(LocalDate.parse(post.getDate().toString()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void modifyPost(ActionEvent event) {
        try {
            LocalDate date = dateField.getValue();
            String description = descriptionField.getText();
            System.out.println("MODIYING ID POST"+id);
            Post post = new Post(id, java.sql.Date.valueOf(date), description);

            servicePost.update(post);
            showSuccessMessage("Post modified successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while modifying the post.");
        }
    }

    @FXML
    void returnTo(MouseEvent event) {
        RouterController router = new RouterController();
        router.navigate("/fxml/PostCrud.fxml");
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okayButton) {
                RouterController router = new RouterController();
                router.navigate("/fxml/PostCrud.fxml");
            }
        });
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void init(Integer id) {
        this.id=id;
        loadData();
    }

    public void modiferpost(ActionEvent actionEvent) {
    }
}
