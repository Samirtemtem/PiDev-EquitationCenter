package Service;


import Entities.Complaint;
import Utils.Datasource;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class ServiceComplaint implements IService<Complaint> {
    private Connection conn = Datasource.getConn();
    private Statement ste;

    @Override
    public void add(Complaint complaint) throws SQLException {
        String query = "INSERT INTO complaint (user_id, description, createdAt,objet,etat) VALUES ( ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getDescription());
            stmt.setDate(3, new java.sql.Date(complaint.getCreatedAt().getTime()));
            stmt.setString(4, complaint.getObjet());
            stmt.setString(5, complaint.getEtat());
            stmt.executeUpdate();
            System.out.println("Reclamation ajouté");
        } catch (SQLException ex) {
            System.out.println(ex);  }
    }
    public void update(Complaint complaint) throws SQLException {
        String query = "UPDATE complaint SET user_id = ?, Description = ?, createdAt = ?, objet = ?, etat = ?,answer=? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getDescription());
            stmt.setDate(3, new java.sql.Date(complaint.getCreatedAt().getTime()));
            stmt.setString(4, complaint.getObjet());
            stmt.setString(5, complaint.getEtat());
            stmt.setString(6, complaint.getAnswer());

            stmt.setInt(7, complaint.getId());
            stmt.executeUpdate();
            System.out.println("Reclamation updated !");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void delete(Complaint complaint) throws SQLException {
        String query = "DELETE FROM Complaint WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaint.getId());
            stmt.executeUpdate();
            System.out.println("Reclamation deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Complaint findById(int id) throws SQLException {
        try {
            ste= conn.createStatement();
        } catch (SQLException ex) {
            System.err.println("erreur");
        }
        try {
            String req = "SELECT * FROM Reclamation where id="+id;
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                Complaint resultComplaint = new Complaint(result.getInt("id"),result.getInt("iduser"), result.getString("objet"), result.getString("Description"), result.getDate("createdAt"),result.getString("etat"));
                return resultComplaint;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public List<Complaint> ReadAll() throws SQLException {
        try {
            System.out.println(conn);
            ste= conn.createStatement();
        } catch (SQLException ex) {
            System.err.println("erreur");
        }
        List<Complaint> rec = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM `Complaint`";
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                Complaint resultComplaint = new Complaint(result.getInt("id"),result.getInt("user_id"), result.getString("objet"), result.getString("Description"), result.getDate("createdAt"),result.getString("etat"));
                resultComplaint.setAnswer(result.getString("answer"));
                rec.add(resultComplaint);
            }
            System.out.println(rec);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rec;
    }
    public List<Complaint> ReadAll(int idUser) throws SQLException {
        try {
            ste= conn.createStatement();
        } catch (SQLException ex) {
            System.err.println("erreur");
        }
        List<Complaint> rec = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM Reclamation where iduser = '" +idUser+"'" ;
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                Complaint resultComplaint = new Complaint(result.getInt("id"),result.getInt("iduser"), result.getString("objet"), result.getString("Description"), result.getDate("createdAt"),result.getString("etat"));

                rec.add(resultComplaint);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rec;
    }
}
