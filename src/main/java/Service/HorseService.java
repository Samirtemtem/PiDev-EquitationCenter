package Service;

import Entities.Horse;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorseService implements IService<Horse> {

    private static Connection conn = Datasource.getConn();

    @Override
    public void add(Horse horse) throws SQLException {
        String query = "INSERT INTO horse (Name, DatePension, Breed, IsAvailable) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, horse.getName());
            stmt.setDate(2, new java.sql.Date(horse.getDatePension().getTime()));
            stmt.setString(3, horse.getBreed());
            stmt.setBoolean(4, horse.getIsAvailable());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Horse horse) throws SQLException {
        String query = "UPDATE horse SET Name = ?, DatePension = ?, Breed = ?, IsAvailable = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, horse.getName());
            stmt.setDate(2, new java.sql.Date(horse.getDatePension().getTime()));
            stmt.setString(3, horse.getBreed());
            stmt.setBoolean(4, horse.getIsAvailable());
            stmt.setInt(5, horse.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Horse horse) throws SQLException {
        String query = "DELETE FROM horse WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, horse.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Horse findById(int id) throws SQLException {
        String query = "SELECT * FROM horse WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createHorseFromResultSet(rs);
                }
            }
        }
        // If no horse is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Horse> ReadAll() throws SQLException {
        List<Horse> horses = new ArrayList<>();
        String query = "SELECT * FROM horse";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Horse horse = createHorseFromResultSet(rs);
                horses.add(horse);
            }
        }
        return horses;
    }

    private Horse createHorseFromResultSet(ResultSet rs) throws SQLException {
        Horse horse = new Horse();
        horse.setId(rs.getInt("id"));
        horse.setName(rs.getString("Name"));
        horse.setDatePension(rs.getDate("DatePension"));
        horse.setBreed(rs.getString("Breed"));
        horse.setIsAvailable(rs.getBoolean("IsAvailable"));
        return horse;
    }

    public List<Horse> getAllHorses() throws SQLException {
        List<Horse> horses = new ArrayList<>();
        String query = "SELECT * FROM horse";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Horse horse = createHorseFromResultSet(rs);
                horses.add(horse);
            }
        }
        return horses;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
