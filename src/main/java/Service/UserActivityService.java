package Service;

import Entities.UserActivity;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserActivityService implements IService<UserActivity> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(UserActivity userActivity) throws SQLException {
        String query = "INSERT INTO useractivity (UserId, ActivityId, Feedback, Stars) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userActivity.getUserId());
            stmt.setInt(2, userActivity.getActivityId());
            stmt.setString(3, userActivity.getFeedback());
            stmt.setInt(4, userActivity.getStars());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(UserActivity userActivity) throws SQLException {
        String query = "UPDATE useractivity SET UserId = ?, ActivityId = ?, Feedback = ?, Stars = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userActivity.getUserId());
            stmt.setInt(2, userActivity.getActivityId());
            stmt.setString(3, userActivity.getFeedback());
            stmt.setInt(4, userActivity.getStars());
            stmt.setInt(5, userActivity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(UserActivity userActivity) throws SQLException {
        String query = "DELETE FROM useractivity WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userActivity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public UserActivity findById(int id) throws SQLException {
        String query = "SELECT * FROM useractivity WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createUserActivityFromResultSet(rs);
                }
            }
        }
        // If no user activity is found, return null or throw an exception
        return null;
    }

    @Override
    public List<UserActivity> ReadAll() throws SQLException {
        List<UserActivity> userActivities = new ArrayList<>();
        String query = "SELECT * FROM useractivity";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserActivity userActivity = createUserActivityFromResultSet(rs);
                userActivities.add(userActivity);
            }
        }
        return userActivities;
    }

    private UserActivity createUserActivityFromResultSet(ResultSet rs) throws SQLException {
        UserActivity userActivity = new UserActivity();
        userActivity.setId(rs.getInt("id"));
        userActivity.setUserId(rs.getInt("UserId"));
        userActivity.setActivityId(rs.getInt("ActivityId"));
        userActivity.setFeedback(rs.getString("Feedback"));
        userActivity.setStars(rs.getInt("Stars"));
        return userActivity;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public int findbyuserIdandActivityId(int userid, int actid) {
        String query = "SELECT * FROM useractivity WHERE UserId = ? AND ActivityId = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            stmt.setInt(2,actid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // If no user activity is found, return null or throw an exception
        return 0;
    }
}
