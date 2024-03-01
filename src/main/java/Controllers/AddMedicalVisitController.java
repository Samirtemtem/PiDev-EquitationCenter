package Controllers;

import Entities.Horse;
import Entities.MedicalVisit;
import Service.HorseService;
import Service.MedicalVisitService;
import Service.VetService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddMedicalVisitController {

    @FXML
    private ComboBox<Horse> horseComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker visitDatePicker;

    private MedicalVisitService medicalVisitService=new MedicalVisitService();

    @FXML
    private void initialize() {
        // Populate the ComboBox with horses from the database
        populateHorseComboBox();

        // Set the cell factory to display horse names in the ComboBox
        horseComboBox.setCellFactory(new Callback<ListView<Horse>, ListCell<Horse>>() {
            @Override
            public ListCell<Horse> call(ListView<Horse> param) {
                return new ListCell<Horse>() {
                    @Override
                    protected void updateItem(Horse item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
    }

    // Method to populate the ComboBox with horses
    private void populateHorseComboBox() {
        HorseService horseService = new HorseService();
        try {
            List<Horse> horses = horseService.ReadAll();
            horseComboBox.getItems().addAll(horses);
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }

    public void returnTo(Event actionEvent)
    {RouterController.navigate("/fxml/MedicalvisitCrud.fxml");
    }
    @FXML
    void addVisit(Event actionEvent) {
        Horse selectedHorse = horseComboBox.getValue();
        String description = descriptionTextField.getText().trim();
        LocalDate visitDate = visitDatePicker.getValue();

        if (selectedHorse == null) {
            showErrorAlert("Please select a horse.");
            return;
        }

        if (description.isEmpty()) {
            showErrorAlert("Please enter a description.");
            return;
        }

        if (visitDate == null) {
            showErrorAlert("Please select a visit date.");
            return;
        }

        try {
            int vetId = getLoggedInVetId();
            MedicalVisit medicalVisit = new MedicalVisit(selectedHorse.getId(), vetId, description, java.sql.Date.valueOf(visitDate));
            medicalVisitService.add(medicalVisit);

            showSuccessAlert("Medical visit added successfully.");
            returnTo(actionEvent);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Failed to add medical visit.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


















    private int getLoggedInVetId() {
        return VetService.user.getId();
    }

    // Other controller methods...
}
