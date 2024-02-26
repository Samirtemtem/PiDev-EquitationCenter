package Service;

import Entities.Activity;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivity implements IService<Activity> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Activity activity) throws SQLException {
        String query = "INSERT INTO activity (Date, TypeActivity, Title, Description, Price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(activity.getDate().getTime()));
            stmt.setString(2, activity.getTypeActivity());
            stmt.setString(3, activity.getTitle());
            stmt.setString(4, activity.getDescription());
            stmt.setDouble(5, activity.getPrice());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Activity activity) throws SQLException {
        String query = "UPDATE activity SET Date = ?, TypeActivity = ?, Title = ?, Description = ?, Price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(activity.getDate().getTime()));
            stmt.setString(2, activity.getTypeActivity());
            stmt.setString(3, activity.getTitle());
            stmt.setString(4, activity.getDescription());
            stmt.setDouble(5, activity.getPrice());
            stmt.setInt(6, activity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Activity activity) throws SQLException {
        String query = "DELETE FROM activity WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, activity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Activity findById(int id) throws SQLException {
        String query = "SELECT * FROM activity WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createActivityFromResultSet(rs);
                }
            }
        }
        // If no activity is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Activity> ReadAll() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activity";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Activity activity = createActivityFromResultSet(rs);
                activities.add(activity);
            }
        }
        return activities;
    }

    private Activity createActivityFromResultSet(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getInt("id"));
        activity.setDate(rs.getDate("Date"));
        activity.setTypeActivity(rs.getString("TypeActivity"));
        activity.setTitle(rs.getString("Title"));
        activity.setDescription(rs.getString("Description"));
        activity.setPrice(rs.getDouble("Price"));
        return activity;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}