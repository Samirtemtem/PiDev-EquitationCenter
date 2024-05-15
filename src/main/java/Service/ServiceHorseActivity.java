package Service;

import Entities.HorseActivity;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceHorseActivity implements IService<HorseActivity> {

    private static Connection conn = Datasource.getConn();

    @Override
    public void add(HorseActivity horseActivity) throws SQLException {
        String query = "INSERT INTO horseActivity (horse_id, activity_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, horseActivity.getHorseId());
            stmt.setInt(2, horseActivity.getActivityId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(HorseActivity horseActivity) throws SQLException {
        // Implement update logic if needed
    }

    @Override
    public void delete(HorseActivity horseActivity) throws SQLException {
        // Implement delete logic if needed
    }

    @Override
    public HorseActivity findById(int id) throws SQLException {
        // Implement findById logic if needed
        return null;
    }

    @Override
    public List<HorseActivity> ReadAll() throws SQLException {
        List<HorseActivity> horseActivities = new ArrayList<>();
        String query = "SELECT * FROM horseActivity";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HorseActivity horseActivity = createHorseActivityFromResultSet(rs);
                horseActivities.add(horseActivity);
            }
        }
        return horseActivities;
    }

    private HorseActivity createHorseActivityFromResultSet(ResultSet rs) throws SQLException {
        int horseId = rs.getInt("horse_id");
        int activityId = rs.getInt("activity_id");
        return new HorseActivity(horseId, activityId);
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public boolean findByHorseAndActivityID(int horseid, int activityid) throws SQLException {
        String query = "SELECT * FROM horseActivity WHERE horse_id = ? AND activity_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, horseid);
            stmt.setInt(2, activityid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a row is found, false otherwise
            }
        }
    }

}
