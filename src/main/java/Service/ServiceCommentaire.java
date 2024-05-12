package Service;

import Entities.Commentaire;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Commentaire commentaire) throws SQLException {
        String query = "INSERT INTO commentaire (Date, Commentaire) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(commentaire.getDate().getTime()));
            stmt.setString(2, commentaire.getCommentaire());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                commentaire.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating commentaire failed, no ID obtained.");
            }
        }
    }

    @Override
    public void update(Commentaire commentaire) throws SQLException {
        String query = "UPDATE commentaire SET Date = ?, Commentaire = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(commentaire.getDate().getTime()));
            stmt.setString(2, commentaire.getCommentaire());
            stmt.setInt(3, commentaire.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Commentaire commentaire) throws SQLException {
        String query = "DELETE FROM commentaire WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, commentaire.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Commentaire findById(int id) throws SQLException {
        String query = "SELECT * FROM commentaire WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createCommentaireFromResultSet(rs);
                }
            }
        }
        return null;
    }


    @Override
    public List<Commentaire> ReadAll() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM commentaire";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Commentaire commentaire = createCommentaireFromResultSet(rs);
                commentaires.add(commentaire);
            }
        }
        return commentaires;
    }

    private Commentaire createCommentaireFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Date date = rs.getDate("Date");
        String commentaire = rs.getString("Commentaire");
        return new Commentaire(id, date, commentaire);
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
