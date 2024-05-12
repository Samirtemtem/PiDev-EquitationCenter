package Controllers;

import Controllers.components.BadWordFilter;
import Entities.Horse;
import Service.ServiceHorse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AddHorseControllers {

    public Label prenomLabel;
    @FXML
    private TextField name;

    @FXML
    private TextField breed;

    @FXML
    private DatePicker DatePension;

    private ServiceHorse horseService = new ServiceHorse();


    @FXML

    public void addHorse(ActionEvent actionEvent) throws Exception {

        String horseName = name.getText().trim();

        String horseBreed = breed.getText().trim();
        LocalDate pensionLocalDate = DatePension.getValue();

        if (horseName.isEmpty() || horseBreed.isEmpty() || pensionLocalDate == null) {
            showErrorAlert("Il faut compléter les données ");
            return;
        }

        if (!horseName.matches("[a-zA-Z ]+")) {
            showErrorAlert("le nom n'est pas valide ");
            return;
        }

        LocalDate currentDate = LocalDate.now();
        if (pensionLocalDate.isAfter(currentDate)) {
            showErrorAlert("Pension date cannot be in the future.");
            return;
        }

        Date pensionDate = java.sql.Date.valueOf(pensionLocalDate);

        Horse horse = new Horse();
        horse.setName(horseName);
        horse.setBreed(horseBreed);
        horse.setDatePension(pensionDate);
        horse.setAvailable(true);

        try {
            horseService.add(horse);
            showSuccessMessage("Horse added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while adding horse.");
        }
    }

    @FXML
    private ImageView btnReturn;

    public void returnTo(MouseEvent mouseEvent) {
        RouterController router = new RouterController();
        router.navigate("/fxml/Vet/HorseCrud.fxml");
        System.out.println("Button Clicked");
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
                router.navigate("/fxml/Vet/HorseCrud.fxml");
                System.out.println("Button Clicked");
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
