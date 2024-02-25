package Controllers;

import Controllers.components.SpinnerIntegerStringConverter;
import Entities.ActivitySession;
import Service.ServiceActivitySession;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Entities.Activity;
import Service.ServiceActivity;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddActivitySessionController {


    @FXML
    private JFXComboBox<Activity> ActivityID;
    @FXML
    private TableView<ActivitySession> sessionTableView;

    @FXML
    private TableColumn<ActivitySession, Integer> idColumn;

    @FXML
    private JFXComboBox<String> dayOfWeekComboBox;

    @FXML
    private TableColumn<ActivitySession, LocalTime> startTimeColumn;

    @FXML
    private TableColumn<ActivitySession, LocalTime> endTimeColumn;

    @FXML
    private TableColumn<ActivitySession, String> dayColumn;
    private final ObservableList<ActivitySession> sessionList = FXCollections.observableArrayList();
    private List<ActivitySession> existingSessions = new ArrayList<>();
    private List<ActivitySession> newSessions = new ArrayList<>();
    private List<ActivitySession> deletedSessions = new ArrayList<>();


    @FXML
    private Label timeLabel;
    @FXML
    private DatePicker StartTime;
    @FXML
    private DatePicker EndTime;
    @FXML
    private Spinner<Integer> endHourSpinner;

    @FXML
    private Spinner<Integer> endMinuteSpinner;
    @FXML
    private Label nomLabel;

    @FXML
    private ImageView btnReturn;
    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private TableColumn<ActivitySession, Void> actionsColumn;
    private final ServiceActivity activityService = new ServiceActivity();

    private void configureSpinnerEditor(Spinner<Integer> spinner) {
        spinner.getEditor().setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            } else {
                return null;
            }
        }));
    }
        @FXML
        void initialize() {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            dayColumn.setCellValueFactory(new PropertyValueFactory<>("weekdayAsString"));

            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button deleteButton = new Button("Supprimer");
                {
                    deleteButton.setOnAction(event -> {
                        ActivitySession session = getTableView().getItems().get(getIndex());
                        if (existingSessions.contains(session)) {
                            existingSessions.remove(session);
                            deletedSessions.add(session);
                        }
                        sessionList.remove(session);
                        sessionTableView.refresh();
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            });
            sessionTableView.setItems(sessionList);

            SpinnerValueFactory<Integer> hourFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
            hourFactory.setConverter(new SpinnerIntegerStringConverter(true));
            hourSpinner.setValueFactory(hourFactory);
            configureSpinnerEditor(hourSpinner);

            SpinnerValueFactory<Integer> minuteFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
            minuteFactory.setConverter(new SpinnerIntegerStringConverter(true));
            minuteSpinner.setValueFactory(minuteFactory);
            configureSpinnerEditor(minuteSpinner);

            SpinnerValueFactory<Integer> endHourFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
            endHourFactory.setConverter(new SpinnerIntegerStringConverter(true));
            endHourSpinner.setValueFactory(endHourFactory);
            configureSpinnerEditor(endHourSpinner);

            SpinnerValueFactory<Integer> endMinuteFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
            endMinuteFactory.setConverter(new SpinnerIntegerStringConverter(true));
            endMinuteSpinner.setValueFactory(endMinuteFactory);
            configureSpinnerEditor(endMinuteSpinner);
            try {
                System.out.println("############################CATCH ENTRY##############################");
                List<Activity> activities = activityService.ReadAll();
                ActivityID.setCellFactory(param -> new ListCell<Activity>() {
                    @Override
                    protected void updateItem(Activity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getId() + " - " + item.getTitle());
                        }
                    }
                });

                ObservableList<Activity> activityOptions = FXCollections.observableArrayList(activities);
                ActivityID.setItems(activityOptions);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ActivityID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ServiceActivitySession sessionService=new ServiceActivitySession();
                    List<ActivitySession> sessions = sessionService.readAllActivitySessions(newValue.getId());
                    existingSessions = sessions;
                    sessionList.clear();

                    sessionList.addAll(sessions);

                    sessionTableView.refresh();
                }
            });
        }

        @FXML
        void returnTo() {
        RouterController.navigate("/fxml/ActivitySession/ActivitySessionCRUD.fxml");
        }

        @FXML
        void AddActivitySession() {
         save();
        }



    @FXML
    void AddSession(ActionEvent actionEvent) {
        Activity selectedActivity = ActivityID.getValue();

        if (selectedActivity == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune activité sélectionnée", "Veuillez sélectionner une activité.");            return;
        }
        int startHour = hourSpinner.getValue();
        int startMinute = minuteSpinner.getValue();

        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        if (startTime.isBefore(endTime)) {
            int selectedDayIndex = dayOfWeekComboBox.getSelectionModel().getSelectedIndex();
            if(selectedDayIndex==-1)
            {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun jour sélectionnée", "Veuillez sélectionner le jour.");
                return;
            }
            Time startTimeTime = Time.valueOf(startHour + ":" + startMinute + ":00");
            Time endTimeTime = Time.valueOf(endHour + ":" + endMinute + ":00");

            ActivitySession newSession = new ActivitySession(
                    selectedActivity.getId(),
                    selectedDayIndex,
                    startTimeTime,
                    endTimeTime
            );
            newSessions.add(newSession);
            sessionList.add(newSession);

            sessionTableView.refresh();

            clearSpinners();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Session d'activité ajoutée","");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Horaire Invalide", "L'heure de début doit être antérieure à l'heure de fin");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private void clearSpinners() {
        hourSpinner.getValueFactory().setValue(0);
        minuteSpinner.getValueFactory().setValue(0);
        endHourSpinner.getValueFactory().setValue(0);
        endMinuteSpinner.getValueFactory().setValue(0);
    }
    private void save() {
        ServiceActivitySession sessionService = new ServiceActivitySession();

        for (ActivitySession session : newSessions) {
            try {
                sessionService.add(session);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (ActivitySession session : deletedSessions) {
            try {
                sessionService.delete(session);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        newSessions.clear();
        deletedSessions.clear();
        showAlert(Alert.AlertType.INFORMATION,"Success","Sauvegardée avec succées","");
    }


}

