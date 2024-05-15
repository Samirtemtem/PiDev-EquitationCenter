package testconnection;

import Entities.ActivitySession;
import Service.ServiceActivitySession;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.time.*;
import java.util.Collections;
import java.util.List;

public class GoogleCalendarView {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

    public void addEventsToGoogleCalendar(List<ActivitySession> events) throws IOException, GeneralSecurityException {
        // Load client secrets
        InputStream in = GoogleCalendarView.class.getResourceAsStream("/credentials.json");
        assert in != null;
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");

        // Build a new authorized API client
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName("Your Application Name")
                .build();

        for (ActivitySession activitySession : events) {
            // Create event start and end DateTimes
            LocalDate currentDate = LocalDate.now().with(DayOfWeek.MONDAY);  // Start of next week
            LocalDate startDate = getNextWeekday(currentDate, activitySession.getWeekday());
            Time startTime = activitySession.getStartTime();
            LocalTime localStartTime = startTime.toLocalTime();

            LocalDateTime startDateTime = startDate.atTime(localStartTime);
            LocalDateTime endDateTime = startDateTime.plusMinutes(60); // Assuming each event lasts for 1 hour

            // Create Google Calendar event
            ServiceActivitySession serviceActivitySession = new ServiceActivitySession();
            String activityName = serviceActivitySession.getActivityNameByActivitySessionID(activitySession.getId());

            Event event = new Event()
                    .setSummary(activityName)
                    .setDescription("Activity Description")
                    .setStart(new EventDateTime().setDateTime(new DateTime(startDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())))
                    .setEnd(new EventDateTime().setDateTime(new DateTime(endDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())));

            // Insert event
       //     service.events().insert("primary", event).execute();
        }
    }

    // Helper method to get the next date of the given weekday
    private LocalDate getNextWeekday(LocalDate currentDate, int targetWeekday) {
        int currentWeekday = currentDate.getDayOfWeek().getValue();

        if (targetWeekday == 0) {
            targetWeekday = 7;
        }

        int daysUntilTarget = (targetWeekday + 7 - currentWeekday) % 7;
        return currentDate.plusDays(daysUntilTarget);
    }
}
