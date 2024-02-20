package Service;

import Entities.Equipment;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipment implements IService<Equipment> {
    private Connection conn = Datasource.getConn();

    @Override
    public void add(Equipment equipment) throws SQLException {
        String query = "INSERT INTO equipment (id, name, type, description, purchaseDate, equipmentCondition) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, equipment.getId());
            stmt.setString(2, equipment.getName());
            stmt.setString(3, equipment.getType());
            stmt.setString(4, equipment.getDescription());
            stmt.setDate(5, new java.sql.Date(equipment.getPurchaseDate().getTime()));
            stmt.setString(6, equipment.getEquipmentCondition());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Equipment equipment) throws SQLException {
        String query = "UPDATE equipment SET name = ?, type = ?, description = ?, purchaseDate = ?, equipmentCondition = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getType());
            stmt.setString(3, equipment.getDescription());
            stmt.setDate(4, new java.sql.Date(equipment.getPurchaseDate().getTime()));
            stmt.setString(5, equipment.getEquipmentCondition());
            stmt.setInt(6, equipment.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Equipment equipment) throws SQLException {
        String query = "DELETE FROM equipment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, equipment.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Equipment findById(int id) throws SQLException {
        String query = "SELECT * FROM equipment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createEquipmentFromResultSet(rs);
                }
            }
        }
        // If no equipment is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Equipment> ReadAll() throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Equipment equipment = createEquipmentFromResultSet(rs);
                equipmentList.add(equipment);
            }
        }
        return equipmentList;
    }

    private Equipment createEquipmentFromResultSet(ResultSet rs) throws SQLException {
        Equipment equipment = new Equipment();
        equipment.setId(rs.getInt("id"));
        equipment.setName(rs.getString("name"));
        equipment.setType(rs.getString("type"));
        equipment.setDescription(rs.getString("description"));
        equipment.setPurchaseDate(rs.getDate("purchaseDate"));
        equipment.setEquipmentCondition(rs.getString("equipmentCondition"));
        return equipment;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}