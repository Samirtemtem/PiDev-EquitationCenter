package Service;

import Entities.ActivitySession;
import Utils.Datasource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivitySession implements IService<ActivitySession> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(ActivitySession session) throws SQLException {
        String query = "INSERT INTO ActivitySession (ActivityID, Weekday, StartTime, EndTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, session.getActivityID());
            stmt.setInt(2, session.getWeekday());
            stmt.setTime(3, session.getStartTime());
            stmt.setTime(4, session.getEndTime());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(ActivitySession session) throws SQLException {
        String query = "UPDATE ActivitySession SET ActivityID = ?, Weekday = ?, StartTime = ?, EndTime = ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, session.getActivityID());
            stmt.setInt(2, session.getWeekday());
            stmt.setTime(3, session.getStartTime());
            stmt.setTime(4, session.getEndTime());
            stmt.setInt(5, session.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(ActivitySession session) throws SQLException {
        String query = "DELETE FROM ActivitySession WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, session.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public ActivitySession findById(int id) throws SQLException {
        String query = "SELECT * FROM ActivitySession WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createActivitySessionFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ActivitySession> ReadAll() throws SQLException {
        List<ActivitySession> sessions = new ArrayList<>();
        String query = "SELECT * FROM ActivitySession";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ActivitySession session = createActivitySessionFromResultSet(rs);
                sessions.add(session);
            }
        }
        return sessions;
    }

    private ActivitySession createActivitySessionFromResultSet(ResultSet rs) throws SQLException {
        ActivitySession session = new ActivitySession();
        session.setId(rs.getInt("ID"));
        session.setActivityID(rs.getInt("ActivityID"));
        session.setWeekday(rs.getInt("Weekday"));
        session.setStartTime(rs.getTime("StartTime"));
        session.setEndTime(rs.getTime("EndTime"));
        return session;
    }

    public List<ActivitySession> readAllActivitySessions() {
        List<ActivitySession> activitySessions = new ArrayList<>();
        String query = "SELECT * FROM activitysession";
        System.out.println("Entering Try");

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                // Retrieve data from the result set and create ActivitySession objects
                int id = rs.getInt("id");
                int activityID = rs.getInt("activityID");
                int weekday = rs.getInt("weekday");
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                ActivitySession activitySession = new ActivitySession(id, activityID, weekday, startTime, endTime);
                activitySessions.add(activitySession);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
        System.out.println(activitySessions);
        return activitySessions;
    }
    public List<ActivitySession> readAllActivitySessions(int activityID) {
        List<ActivitySession> activitySessions = new ArrayList<>();
        String query = "SELECT * FROM activitysession WHERE ActivityID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, activityID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int weekday = rs.getInt("weekday");
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    ActivitySession activitySession = new ActivitySession(id, activityID, weekday, startTime, endTime);
                    activitySessions.add(activitySession);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activitySessions;
    }
    public List<ActivitySession> readAllActivitySessionByWeekDayNumber(Integer weekday) {
        List<ActivitySession> activitySessions = new ArrayList<>();
        String query = "SELECT * FROM activitysession WHERE weekday = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, weekday);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int activityID = rs.getInt("activityID");
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    ActivitySession activitySession = new ActivitySession(id, activityID, weekday, startTime, endTime);
                    activitySessions.add(activitySession);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activitySessions;
    }
    public String getActivityNameByActivitySessionID(int activitySessionID) {
        String activityName = null;
        String query = "SELECT a.Title FROM activity a " +
                "JOIN ActivitySession s ON a.id = s.ActivityID " +
                "WHERE s.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, activitySessionID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    activityName = rs.getString("Title");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityName;
    }

}
