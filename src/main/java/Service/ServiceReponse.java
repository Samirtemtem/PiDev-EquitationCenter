package Service;

import Entities.Reponse;
import Utils.Datasource;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IService <Reponse> {
  private Connection conn = Datasource.getConn();
  private Statement ste;
  @Override
  public void add(Reponse reponse) throws SQLException {
    String query = "INSERT INTO Reponse (contenuRep,idComplaint) VALUES (?, ?)";

    try( PreparedStatement stmt = conn.prepareStatement(query)){
      stmt.setString(1, reponse.getContenuRep());
      stmt.setInt(2, reponse.getIdComplaint());
      stmt.executeUpdate();
      System.out.println("Reponse ajouté");
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());   }
  }

  @Override
  public void update(Reponse reponse) throws SQLException {

    String query = "UPDATE Reponse SET ContenuRep = ?, id = ? WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, reponse.getContenuRep());
      stmt.setInt(2, reponse.getId());
      stmt.executeUpdate();
      System.out.println("Reponse updated !");
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }

  @Override
  public void delete(Reponse reponse) throws SQLException {
    String query = "DELETE FROM Reponse WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, reponse.getId());
      stmt.executeUpdate();
      System.out.println("Reponse deleted !");
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }

  @Override
  public Reponse findById(int id) throws SQLException {
    try {
      ste= conn.createStatement();
    } catch (SQLException ex) {
      System.err.println("erreur");
    }
    try {
      String req = "SELECT * FROM `Reponse` where idComplaint="+id;
      ResultSet result = ste.executeQuery(req);

      while (result.next()) {
        Reponse resultReponse = new Reponse(result.getInt(1), result.getDate(2), result.getString(3),result.getInt(4));
        return resultReponse;
      }

    } catch (SQLException ex) {
      System.out.println(ex);
    }
    return null;

  }

  @Override
  public List<Reponse> ReadAll() throws SQLException {
    try {
      ste= conn.createStatement();
    } catch (SQLException ex) {
      System.err.println("erreur");
    }
    List<Reponse> rec = new ArrayList<Reponse>();
    try {
      String req = "SELECT * FROM `Reponse`";
      ResultSet result = ste.executeQuery(req);

      while (result.next()) {
        Reponse resultReponse = new Reponse(result.getInt(1), result.getDate(2), result.getString(3),result.getInt(4));
        rec.add(resultReponse);
      }
      System.out.println(rec);

    } catch (SQLException ex) {
      System.out.println(ex);
    }
    return rec;
  }

  public Reponse getReponseByIdReclamation(int id) {
    try {
      ste= conn.createStatement();
    } catch (SQLException ex) {
      System.err.println("erreur");
    }
    try {
      String req = "SELECT * FROM `Reponse` where idreclamation="+id;
      ResultSet result = ste.executeQuery(req);

      while (result.next()) {
        Reponse resultReponse = new Reponse(result.getInt(1), result.getDate(2), result.getString(3),result.getInt(4));
        return resultReponse;
      }

    } catch (SQLException ex) {
      System.out.println(ex);
    }
    return null;

  }
}
