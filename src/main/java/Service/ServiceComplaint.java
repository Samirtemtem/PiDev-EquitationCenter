package Service;

import Entities.Complaint;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceComplaint implements IService<Complaint> {
    private Connection conn = Datasource.getConn();

    @Override
    public void add(Complaint complaint) throws SQLException {
        String query = "INSERT INTO complaint (id, userId, complaintText, createdAt, status, priority) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getId());
            stmt.setInt(2, complaint.getUserId());
            stmt.setString(3, complaint.getComplaintText());
            stmt.setDate(4, new java.sql.Date(complaint.getCreatedAt().getTime()));
            stmt.setString(5, complaint.getStatus());
            stmt.setString(6, complaint.getPriority());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Complaint complaint) throws SQLException {
        String query = "UPDATE complaint SET userId = ?, complaintText = ?, createdAt = ?, status = ?, priority = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getComplaintText());
            stmt.setDate(3, new java.sql.Date(complaint.getCreatedAt().getTime()));
            stmt.setString(4, complaint.getStatus());
            stmt.setString(5, complaint.getPriority());
            stmt.setInt(6, complaint.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Complaint complaint) throws SQLException {
        String query = "DELETE FROM complaint WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Complaint findById(int id) throws SQLException {
        String query = "SELECT * FROM complaint WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createComplaintFromResultSet(rs);
                }
            }
        }
        // If no complaint is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Complaint> ReadAll() throws SQLException {
        List<Complaint> complaintList = new ArrayList<>();
        String query = "SELECT * FROM complaint";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Complaint complaint = createComplaintFromResultSet(rs);
                complaintList.add(complaint);
            }
        }
        return complaintList;
    }

    private Complaint createComplaintFromResultSet(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setUserId(rs.getInt("userId"));
        complaint.setComplaintText(rs.getString("complaintText"));
        complaint.setCreatedAt(rs.getDate("createdAt"));
        complaint.setStatus(rs.getString("status"));
        complaint.setPriority(rs.getString("priority"));
        return complaint;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}