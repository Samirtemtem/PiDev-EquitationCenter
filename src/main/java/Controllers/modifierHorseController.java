package Controllers;

import Entities.Horse;
import Service.HorseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class modifierHorseController implements Initializable,InitializableController{

    private int horseId;

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    @FXML
    private TextField name;

    @FXML
    private TextField breed;

    @FXML
    private DatePicker datePension;

    @FXML
    private CheckBox IsAvailable;

    private HorseService horseService = new HorseService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(int horseId) throws SQLException {
        this.horseId = horseId;
        System.out.println("##############"+horseId);
        loadData();
    }

    private void loadData() throws SQLException {

        Horse horse = horseService.findById(horseId);
        System.out.println("##############"+horse.getDatePension());
        if (horse != null) {
            name.setText(horse.getName());
            breed.setText(horse.getBreed());
            datePension.setValue(LocalDate.parse(horse.getDatePension().toString()));
            IsAvailable.setSelected(horse.getIsAvailable());
        } else {
            System.out.println("Cheval non trouvé.");
        }

    }

    @FXML
    void modifierHorse(ActionEvent event) {
        try {
            String name = this.name.getText();
            String breed = this.breed.getText();
            java.util.Date datePension = java.sql.Date.valueOf(this.datePension.getValue());
            boolean isAvailable = IsAvailable.isSelected();

            horseService.update(new Horse(horseId, name, datePension, breed, isAvailable));

            showSuccessMessage("Cheval modifié avec succès");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                try {
                    RouterController router = new RouterController();
                    router.navigate("/fxml/Vet/HorseCrud.fxml");
                    System.out.println("Button Clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void returnTo(MouseEvent mouseEvent) {
        System.out.println("RETURN TO EXECUTED");
        RouterController router;
        router = new RouterController();
        router.navigate("/fxml/Vet/HorseCrud.fxml");
    }

    @Override
    public void init(Integer activityId) throws SQLException {
        this.horseId = activityId;
        loadData();
    }
}