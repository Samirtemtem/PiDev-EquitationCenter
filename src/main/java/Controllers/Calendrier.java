package Controllers;

import Entities.ActivitySession;
import Service.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Calendrier {

    @FXML
    private VBox calendrierBox;
    private ServiceActivitySession serviceActivitySession;

    public Calendrier() {
        serviceActivitySession = new ServiceActivitySession();
    }

    public void initialize() {
        Agenda agenda = new Agenda();
        agenda.setLocale(Locale.FRENCH);

        // Apply CSS styles to the agenda
        agenda.getStyleClass().addAll("agenda", "style1");

        // Create a label for the current month
        LocalDate currentDate = LocalDate.now();
        Label monthLabel = new Label(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH) + " " + currentDate.getYear());
        monthLabel.getStyleClass().add("month-label");

        calendrierBox.getChildren().addAll(monthLabel, agenda);
        calendrierBox.setStyle("-fx-background-color: #6ce3d6;");


        List<ActivitySession> events = serviceActivitySession.readAllActivitySessions();
        addEventsToAgenda(events, agenda);
    }



    public void addEventsToAgenda(List<ActivitySession> events, Agenda agenda) {
        for (ActivitySession activitySession : events) {
            int weekday = activitySession.getWeekday();
            LocalDate currentDate = LocalDate.now().with(DayOfWeek.MONDAY);
            LocalDate startDate = getNextWeekday(currentDate, weekday);
            Time startTime = activitySession.getStartTime();
            LocalTime localStartTime = startTime.toLocalTime();

            LocalDateTime start = startDate.atTime(localStartTime);

            LocalDateTime end = startDate.atTime(activitySession.getEndTime().toLocalTime());

            if (weekday == 0) {
                start = start.with(DayOfWeek.SUNDAY);
                end = end.with(DayOfWeek.SUNDAY);
            }

            String activityName = serviceActivitySession.getActivityNameByActivitySessionID(activitySession.getId());
            System.out.println(activityName);
            agenda.appointments().add(new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(start)
                    .withEndLocalDateTime(end)
                    .withSummary(activityName));
        }
    }

    private LocalDate getNextWeekday(LocalDate currentDate, int targetWeekday) {
        int currentWeekday = currentDate.getDayOfWeek().getValue();

        if (targetWeekday == 0) {
            targetWeekday = 7;
        }

        int daysUntilTarget = (targetWeekday + 7 - currentWeekday) % 7;
        return currentDate.plusDays(daysUntilTarget);
    }


}
