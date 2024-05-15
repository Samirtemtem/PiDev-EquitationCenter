package Controllers;

import Entities.ActivitySession;
import Service.ServiceActivitySession;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Service.ServiceActivity;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySessionCrudController {
    @FXML
    private TableView<ActivitySession> tableView;
    @FXML
    private Label adminNameLabel;

    @FXML
    private TableColumn<ActivitySession, Integer> activityIDColumn;

    @FXML
    private TableColumn<ActivitySession, Integer> numberOfSessionsColumn;

    @FXML
    private TableColumn<ActivitySession, String> sessionsColumn;

    private final ObservableList<ActivitySession> activitySessionList = FXCollections.observableArrayList();


        public void initialize() {
            GuiLoginController guilogin = new GuiLoginController();
            String name="Bienvenue "+guilogin.user.getName()+"!";
            adminNameLabel.setText(name);

            TableColumn<ActivitySession, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ActivitySession, String> activityIDCol = new TableColumn<>("Activity ID");
            activityIDCol.setCellValueFactory(cellData -> {
                int id = cellData.getValue().getId();
                String activityName = null;
                try {
                    ServiceActivity sa=new ServiceActivity();
                    activityName = sa.findById(id).getTitle();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return new SimpleStringProperty(id + " / " + activityName);
            });

            TableColumn<ActivitySession, Integer> numberOfSessionsCol = new TableColumn<>("Number of Sessions");

            TableColumn<ActivitySession, String> sessionsCol = new TableColumn<>("Sessions");
            sessionsCol.setCellValueFactory(cellData -> cellData.getValue().sessionsProperty());

            retrieveActivitySessionData();
            tableView.setItems(activitySessionList);

        }
    private final ServiceActivitySession activitySessionService = new ServiceActivitySession();


    private void retrieveActivitySessionData() {
        Task<Map<Integer, List<ActivitySession>>> task = new Task<>() {
            @Override
            protected Map<Integer, List<ActivitySession>> call() throws Exception {
                // Retrieve all activity sessions
                List<ActivitySession> allActivitySessions = activitySessionService.readAllActivitySessions();
                // Initialize a map to store activityID and corresponding sessions
                Map<Integer, List<ActivitySession>> activitySessionsMap = new HashMap<>();

                // Group activity sessions by activityID
                for (ActivitySession activitySession : allActivitySessions) {
                    int activityID = activitySession.getActivityID();
                    if (!activitySessionsMap.containsKey(activityID)) {
                        activitySessionsMap.put(activityID, new ArrayList<>());
                    }
                    activitySessionsMap.get(activityID).add(activitySession);
                }

                return activitySessionsMap;
            }
        };

        task.setOnSucceeded(event -> {
            Map<Integer, List<ActivitySession>> activitySessionsMap = task.getValue();
            activitySessionList.clear();

            for (Map.Entry<Integer, List<ActivitySession>> entry : activitySessionsMap.entrySet()) {
                int activityID = entry.getKey();
                List<ActivitySession> activitySessions = entry.getValue();

                int numberOfSessions = activitySessions.size();

                ActivitySession activitySession = new ActivitySession();
                activitySession.setActivityID(activityID);
                activitySession.setNumberOfSessions(numberOfSessions);
                StringBuilder sessionsBuilder = new StringBuilder();
                for (ActivitySession session : activitySessions) {
                    String sessionInfo = session.getWeekdayAsString() + " de " + session.getStartTime() + " à " + session.getEndTime() + "\n";
                    sessionsBuilder.append(sessionInfo);
                }

                activitySession.setSessions(sessionsBuilder.toString());

                System.out.println("Activitysession.sessions content: " + activitySession.getSessions());

                activitySessionList.add(activitySession);
                for (ActivitySession as : activitySessionList) {
                    ServiceActivity sa=new ServiceActivity();
                    String activityName = null; //
                    try {
                        activityName = sa.findById(activitySession.getActivityID()).getTitle();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    activitySession.setActivityName(as.getActivityID() + " - " + activityName);
                }
                System.out.println(activitySessionList);
            }
        });

        new Thread(task).start();
    }
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/Admin/AdminDashboard.fxml");

    }

    public void searchquery(KeyEvent keyEvent) {
    }

    public void gotoAjouter(ActionEvent actionEvent) {
        RouterController.navigate("../fxml/ActivitySession/ActivitySessionAdd.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/Activities/ActivitiesCRUD.fxml");
    }

    public void GoToActivitySessions(MouseEvent mouseEvent) {
        RouterController.navigate("../fxml/ActivitySession/ActivitySessionCRUD.fxml");
    }
}
