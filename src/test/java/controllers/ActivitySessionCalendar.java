package controllers;
import Entities.ActivitySession;
import Service.ServiceActivitySession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
public class ActivitySessionCalendar {


        @FXML
        private Text year;

        @FXML
        private Text month;

        @FXML
        private FlowPane calendar;

        private final ServiceActivitySession service = new ServiceActivitySession();

        @FXML
        public void initialize() {
            LocalDate dateFocus = LocalDate.now();
            drawCalendar(dateFocus);
        }

        @FXML
        void backOneMonth(ActionEvent event) {
            LocalDate dateFocus = LocalDate.now().minusMonths(1);
            drawCalendar(dateFocus);
        }

        @FXML
        void forwardOneMonth(ActionEvent event) {
            LocalDate dateFocus = LocalDate.now().plusMonths(1);
            drawCalendar(dateFocus);
        }

        private void drawCalendar(LocalDate dateFocus) {
            year.setText(String.valueOf(dateFocus.getYear()));
            month.setText(String.valueOf(dateFocus.getMonth()));

            int monthMaxDays = dateFocus.lengthOfMonth();
            LocalDate firstOfMonth = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1);
            DayOfWeek firstDayOfWeek = firstOfMonth.getDayOfWeek();
            int daysToMonday = (8 - firstDayOfWeek.getValue()) % 7;
            LocalDate startDate = firstOfMonth.plusDays(daysToMonday);

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    StackPane stackPane = new StackPane();

                    Rectangle rectangle = new Rectangle();
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.BLACK);
                    double rectangleWidth = calendar.getPrefWidth() / 7;
                    double rectangleHeight = calendar.getPrefHeight() / 6;
                    rectangle.setWidth(rectangleWidth);
                    rectangle.setHeight(rectangleHeight);
                    stackPane.getChildren().add(rectangle);

                    LocalDate currentDate = startDate.plusDays((i * 7) + j);
                    Text dateText = new Text(String.valueOf(currentDate.getDayOfMonth()));
                    stackPane.getChildren().add(dateText);

                    List<ActivitySession> sessions = service.readAllActivitySessionsByDate(currentDate);
                    if (!sessions.isEmpty()) {
                        addSessionsToStackPane(stackPane, sessions);
                    }

                    calendar.getChildren().add(stackPane);
                }
            }
        }

        private void addSessionsToStackPane(StackPane stackPane, List<ActivitySession> sessions) {
            StringBuilder sessionText = new StringBuilder();
            for (ActivitySession session : sessions) {
                sessionText.append(session.getStartTime()).append(" - ").append(session.getEndTime()).append("\n");
            }

            Text sessionsText = new Text(sessionText.toString());
            sessionsText.setStyle("-fx-font-size: 10px;");
            stackPane.getChildren().add(sessionsText);
        }
    }

