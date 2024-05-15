package Service;

import Entities.User;
import Entities.Vet;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VetService implements IService<Vet> {

    private Connection conn = Datasource.getConn();
    public static Vet user;

    @Override
    public void add(Vet vet) throws SQLException {
        String query = "INSERT INTO vet (id, name, Email, Password, dateJoined) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vet.getId());
            stmt.setString(2, vet.getName());
            stmt.setString(3, vet.getEmail());
            stmt.setString(4, vet.getPassword());
            stmt.setDate(5, new java.sql.Date(vet.getDateJoined().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Vet vet) throws SQLException {
        String query = "UPDATE vet SET name = ?, Email = ?, Password = ?, dateJoined = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vet.getName());
            stmt.setString(2, vet.getEmail());
            stmt.setString(3, vet.getPassword());
            stmt.setDate(4, new java.sql.Date(vet.getDateJoined().getTime()));
            stmt.setInt(5, vet.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Vet vet) throws SQLException {
        String query = "DELETE FROM vet WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vet.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Vet findById(int id) throws SQLException {
        String query = "SELECT * FROM vet WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createVetFromResultSet(rs);
                }
            }
        }
        // If no vet is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Vet> ReadAll() throws SQLException {
        List<Vet> vets = new ArrayList<>();
        String query = "SELECT * FROM vet";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vet vet = createVetFromResultSet(rs);
                vets.add(vet);
            }
        }
        return vets;
    }

    private Vet createVetFromResultSet(ResultSet rs) throws SQLException {
        Vet vet = new Vet();
        vet.setId(rs.getInt("id"));
        vet.setName(rs.getString("name"));
        vet.setEmail(rs.getString("Email"));
        vet.setPassword(rs.getString("Password"));
        vet.setDateJoined(rs.getDate("dateJoined"));
        return vet;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Vet Login(String email, String password) throws SQLException {
        String query = "SELECT * FROM Vet WHERE email = ? AND password = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Vet vet = new Vet();
                vet.setId(resultSet.getInt("id"));
                vet.setEmail(resultSet.getString("email"));
                vet.setPassword(resultSet.getString("password"));
                vet.setName(resultSet.getString("name"));
                this.user=vet;
                return vet; // Admin found with the given email and password

            }
        }
        return null; // No admin found with the given email and password
    }


}
