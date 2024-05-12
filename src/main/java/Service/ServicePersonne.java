package Service;

//import Entities.Personne;

import java.sql.*;
import java.util.*;
import Utils.Datasource;
/*public class ServicePersonne implements IService<Personne> {
    private Connection conn = Datasource.getConn();
    private Statement ste;

    public ServicePersonne() throws SQLException {
        ste=conn.createStatement();
    }
    @Override
    public void add(Personne personne) throws SQLException {

    }

    @Override
    public void update(Personne personne) throws SQLException {

    }

    @Override
    public void delete(Personne personne) throws SQLException {

    }

    @Override
    public Personne findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Personne> ReadAll(Personne personne) throws SQLException {
        List<Personne> p = null;
        String query= "Select * from `personne`";
        ResultSet r =  ste.executeQuery(query);
        ResultSetMetaData rsmd = r.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while(r.next()) {
            int id = r.getInt("id");
            String nom = r.getString("nom");
            String prenom = r.getString("prenom");
            int age = r.getInt("age");
            p.add(new Personne(nom,prenom,age));
        }
    }
}
*/