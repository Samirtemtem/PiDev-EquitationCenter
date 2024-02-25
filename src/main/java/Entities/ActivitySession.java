package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.sql.Time;

public class ActivitySession {
    private int id;
    private int activityID;
    private int weekday;
    private Time startTime;
    private Time endTime;

    public ActivitySession(int id, int activityID, int weekday, Time startTime, Time endTime, String sessions,int numberOfSessions) {

        this.id = id;
        this.activityID = activityID;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfSessions = numberOfSessions;
        this.sessions = sessions;
    }
    public ActivitySession(int activityID, int weekday, Time startTime, Time endTime) {
        this.activityID = activityID;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public ActivitySession(int id, int activityID, int weekday, Time startTime, Time endTime) {
        this.id = id;
        this.activityID = activityID;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    // toString method


    @Override
    public String toString() {
        return "ActivitySession{" +
                "id=" + id +
                ", activityID=" + activityID +
                ", weekday=" + weekday +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", numberOfSessions=" + numberOfSessions +
                ", sessions='" + sessions + '\'' +
                '}';
    }

    public String getWeekdayAsString() {
        switch (weekday) {
            case 0:
                return "Dimance";
            case 1:
                return "Lundi";
            case 2:
                return "Mardi";
            case 3:
                return "Mercredi";
            case 4:
                return "Jeudi";
            case 5:
                return "Vendredi";
            case 6:
                return "Samedi";
            default:
                return "N/A";
        }
    }
    public ObservableValue<String> sessionsProperty() {
            return new SimpleStringProperty(sessions);

    }

    private int numberOfSessions;

    public void setNumberOfSessions(int numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    // Getter for numberOfSessions
    public int getNumberOfSessions() {
        return numberOfSessions;
    }
    private String sessions;

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }
    public ActivitySession() {

    }
    public String ActivityName;
    public String getActivityName()
    {
        return this.ActivityName;
    }
    public void setActivityName(String s) {
        this.ActivityName = s;
    }


}


