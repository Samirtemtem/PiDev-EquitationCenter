package Controllers;

import Entities.Horse;
import Entities.MedicalVisit;
import Service.HorseService;
import Service.MedicalVisitService;
import Service.VetService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierMedicalVisitController implements Initializable, InitializableController {

    @FXML
    private ComboBox<Horse> horseComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker visitDatePicker;

    private MedicalVisitService medicalVisitService=new MedicalVisitService();

    private int medicalVisitID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateHorseComboBox();
        setupComboBoxCellFactory();
    }

    private void populateHorseComboBox() {
        HorseService horseService = new HorseService();
        try {
            List<Horse> horses = horseService.ReadAll();
            horseComboBox.getItems().addAll(horses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupComboBoxCellFactory() {
        horseComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Horse>() {
            @Override
            protected void updateItem(Horse item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    @FXML
    void addVisit(ActionEvent event) {
        Horse selectedHorse = horseComboBox.getValue();
        String description = descriptionTextField.getText();
        LocalDate visitDate = visitDatePicker.getValue();

        if (selectedHorse == null || description.isEmpty() || visitDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        try {
            int horseId = selectedHorse.getId();
            int vetId = getLoggedInVetId();

            MedicalVisit medicalVisit = new MedicalVisit(medicalVisitID,horseId, vetId, description, java.sql.Date.valueOf(visitDate));
            medicalVisitService.update(medicalVisit);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Medical visit added successfully");
            alert.showAndWait();

            clearFields();
            Event Event = null;
            returnTo(Event);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add medical visit");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        horseComboBox.setValue(null);
        descriptionTextField.clear();
        visitDatePicker.setValue(null);
    }

    private int getLoggedInVetId() {
        return GuiLoginController.user.getId();
    }



    @Override
    public void init(Integer activityId) {
        System.out.println(activityId);

        this.medicalVisitID = activityId;
        loadfields();

    }
    public void loadfields()
    {
        MedicalVisit MV;
        MedicalVisitService MVS=new MedicalVisitService();
        System.out.println(this.medicalVisitID);

        try {
            MV=MVS.findById(this.medicalVisitID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        descriptionTextField.setText(MV.getDescription());
        Date visitDate = MV.getVisitDate();
        java.util.Date date = MV.getVisitDate();
        visitDatePicker.setValue(LocalDate.parse(MV.getVisitDate().toString()));


        HorseService HS=new HorseService();
        try {
            horseComboBox.setValue(HS.findById(MV.getIdHorse()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void returnTo(Event actionEvent){

        RouterController.navigate("/fxml/Vet/MedicalvisitCrud.fxml");
    }
}
