package Service;

import Entities.MedicalVisit;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalVisitService implements IService<MedicalVisit> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(MedicalVisit medicalVisit) throws SQLException {
        String query = "INSERT INTO medicalvisit (idHorse, idVet, Description, VisitDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, medicalVisit.getIdHorse());
            stmt.setInt(2, medicalVisit.getIdVet());
            stmt.setString(3, medicalVisit.getDescription());
            stmt.setDate(4, new java.sql.Date(medicalVisit.getVisitDate().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(MedicalVisit medicalVisit) throws SQLException {
        String query = "UPDATE medicalvisit SET idHorse = ?, idVet = ?, Description = ?, VisitDate = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, medicalVisit.getIdHorse());
            stmt.setInt(2, medicalVisit.getIdVet());
            stmt.setString(3, medicalVisit.getDescription());
            stmt.setDate(4, new java.sql.Date(medicalVisit.getVisitDate().getTime()));
            stmt.setInt(5, medicalVisit.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(MedicalVisit medicalVisit) throws SQLException {
        String query = "DELETE FROM medicalvisit WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, medicalVisit.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public MedicalVisit findById(int id) throws SQLException {
        String query = "SELECT * FROM medicalvisit WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createMedicalVisitFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<MedicalVisit> ReadAll() throws SQLException {
        List<MedicalVisit> medicalVisits = new ArrayList<>();
        String query = "SELECT * FROM medicalvisit";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MedicalVisit medicalVisit = createMedicalVisitFromResultSet(rs);
                medicalVisits.add(medicalVisit);
            }
        }
        return medicalVisits;
    }

    private MedicalVisit createMedicalVisitFromResultSet(ResultSet rs) throws SQLException {
        MedicalVisit medicalVisit = new MedicalVisit();
        medicalVisit.setId(rs.getInt("id"));
        medicalVisit.setIdHorse(rs.getInt("idHorse"));
        medicalVisit.setIdVet(rs.getInt("idVet"));
        medicalVisit.setDescription(rs.getString("Description"));
        medicalVisit.setVisitDate(rs.getDate("VisitDate"));
        return medicalVisit;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
