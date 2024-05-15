package Service;

import Entities.Activity;
import Entities.ActivityType;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivity implements IService<Activity> {

    private Connection conn = Datasource.getConn();

    public int add(Activity activity,String id) throws SQLException {
        String query = "INSERT INTO activity (title, TypeActivity, price, date, description, imageData) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, activity.getTitle());
            stmt.setString(2, activity.getTypeActivity());
            stmt.setDouble(3, activity.getPrice());
            stmt.setDate(4, new java.sql.Date(activity.getDate().getTime()));
            stmt.setString(5, activity.getDescription());
            stmt.setBytes(6, activity.getImageData());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating activity failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int activityId = generatedKeys.getInt(1);
                    return activityId;
                } else {
                    throw new SQLException("Creating activity failed, no ID obtained.");
                }
            }
        }
    }
    @Override
    public void add(Activity activity) throws SQLException {
        String query = "INSERT INTO activity (Date, TypeActivity, Title, Description, Price, ImageData) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(activity.getDate().getTime()));
            stmt.setString(2, activity.getTypeActivity()); // Use enum name for insertion
            stmt.setString(3, activity.getTitle());
            stmt.setString(4, activity.getDescription());
            stmt.setDouble(5, activity.getPrice());
            stmt.setBytes(6, activity.getImageData()); // Set image data as byte array
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Activity activity) throws SQLException {
        String query = "UPDATE activity SET Date = ?, TypeActivity = ?, Title = ?, Description = ?, Price = ?, ImageData = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(activity.getDate().getTime()));
            stmt.setString(2, activity.getTypeActivity()); // Use enum name for insertion
            stmt.setString(3, activity.getTitle());
            stmt.setString(4, activity.getDescription());
            stmt.setDouble(5, activity.getPrice());
            stmt.setBytes(6, activity.getImageData()); // Set image data as byte array
            stmt.setInt(7, activity.getId());
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
            System.out.println(id);
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
    public List<Activity> readPage(int pageNumber, int pageSize) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activity LIMIT ? OFFSET ?";
        int offset = (pageNumber - 1) * pageSize;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Activity activity = createActivityFromResultSet(rs);
                    activities.add(activity);
                }
            }
        }
        return activities;
    }

    public int countAll() throws SQLException {
        String query = "SELECT COUNT(*) FROM activity";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }


    private Activity createActivityFromResultSet(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getInt("id"));
        activity.setDate(rs.getDate("Date"));
        String typeActivityName = rs.getString("TypeActivity");
        if (typeActivityName != null) {
            activity.setTypeActivity(typeActivityName);
        }        activity.setTitle(rs.getString("Title"));
        activity.setDescription(rs.getString("Description"));
        activity.setPrice(rs.getDouble("Price"));

        // Retrieve the Blob data
        Blob blob = rs.getBlob("ImageData");
        if (blob != null) {
            byte[] imageData = blob.getBytes(1, (int) blob.length());
            // Set the Blob data in the Activity object
            activity.setImageData(imageData);
        }

        return activity;
    }
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
        public List<Activity> searchActivities(String query) throws SQLException {
            List<Activity> searchResult = new ArrayList<>();
            String searchQuery = "SELECT * FROM activity WHERE Title LIKE ? OR Description LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(searchQuery)) {
                String searchParam = "%" + query + "%";
                stmt.setString(1, searchParam);
                stmt.setString(2, searchParam);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Activity activity = createActivityFromResultSet(rs);
                        searchResult.add(activity);
                    }
                }
            }
            return searchResult;
        }

    }