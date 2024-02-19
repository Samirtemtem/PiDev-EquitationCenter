package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomSuccessDialogController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button okButton;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    @FXML
    void handleOKButton(ActionEvent event) {
        dialogStage.close();
    }
}
