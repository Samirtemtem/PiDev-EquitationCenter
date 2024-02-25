package Controllers;

import Entities.ActivitySession;
import Service.ServiceActivity;
import Service.ServiceActivitySession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class ActivitySessionCalendar {

    @FXML
    private FlowPane calendar;

    private final ServiceActivitySession service = new ServiceActivitySession();

    @FXML
    public void initialize() throws SQLException {
        drawCalendar();
    }


    private void drawCalendar() throws SQLException {

        for (int i = 0; i < 7; i++) {
            int weekday = i;
            List<ActivitySession> sessions = service.readAllActivitySessionByWeekDayNumber(weekday);

            StackPane stackPane = new StackPane();
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            double rectangleWidth = calendar.getPrefWidth() / 8;
            double rectangleHeight = calendar.getPrefHeight() / 6;
            rectangle.setWidth(rectangleWidth);
            rectangle.setHeight(rectangleHeight);
            stackPane.getChildren().add(rectangle);

            if (!sessions.isEmpty()) {
                addSessionsToStackPane(stackPane, sessions);
            }

            calendar.getChildren().add(stackPane);
        }
    }

    private void addSessionsToStackPane(StackPane stackPane, List<ActivitySession> sessions) throws SQLException {
        StringBuilder sessionText = new StringBuilder();
        ServiceActivity sa=new ServiceActivity();
        for (ActivitySession session : sessions) {
            String activityName = sa.findById(session.getActivityID()).getTitle();
            sessionText.append(activityName)
                    .append(" - ")
                    .append(session.getStartTime())
                    .append(" - ")
                    .append(session.getEndTime())
                    .append("\n");
        }

        Text sessionsText = new Text(sessionText.toString());
        sessionsText.setStyle("-fx-font-size: 10px;");
        stackPane.getChildren().add(sessionsText);
    }
}
