package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Role;
import Utils.PassSecurity;


public class ServiceUser implements IService<User> {
    private Connection connection = Datasource.getConn();
    PassSecurity ps =new PassSecurity();

    public ServiceUser() {
    }

    @Override
    public void add(User user) throws SQLException {
        byte[] salt = ps.generateSalt();
        //hash the password withe salt
        if(emailExists(user.getEmail())){
            System.out.println("User with email"+user.getEmail()+"already exists.");
            return ;// Exit the method if email exists
        }

    String query = "INSERT INTO user (email, hash,salt, nom,prenom,address,num_tel,dateJoined, role,ImageData) VALUES (?,?, ?, ?, ?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            String hashedPassword = ps.hashPassword(user.getPassword(), salt);
            statement.setString(2, hashedPassword);
            statement.setBytes(3, salt);
            statement.setString(4, user.getNom());
            statement.setString(5, user.getPrenom());
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getNum_tel());
            statement.setDate(8, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setString(9, user.getRole().toString());
            statement.setBytes(10, user.getImageData()); // Set image data as byte array
            statement.executeUpdate();
        }
    }






    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE user SET email = ?, password = ?, nom = ?, prenom = ?,address = ?,num_tel = ?,dateJoined = ?,role = ? ,ImageData = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getNom());
            statement.setString(4, user.getPrenom());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getNum_tel());
            statement.setDate(7, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setString(8, user.getRole().toString());
            statement.setBytes(9, user.getImageData()); // Set image data as byte array
            statement.setInt(10, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            System.out.println("utilisateur deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRole(user.role.valueOf(resultSet.getString("role")));
                return user;
            }
            return null; // No user found with the given ID
        }
    }

    @Override
    public List<User> ReadAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRole(user.role.valueOf(resultSet.getString("role")));
                users.add(user);
            }
        }
        return users;
    }

    public User login(String email, String password, Role role) throws SQLException {
        User user = null;
        if (!emailExists(email)) {
            System.out.println("email doesn't exist");
            return null;
        }
        String query = "SELECT * FROM user WHERE email = ?  AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
           // statement.setString(2, password);
            statement.setString(2, role.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] salt = resultSet.getBytes("salt");
                String hashedPassword = resultSet.getString("hash");
                if (ps.validatePassword(password, hashedPassword, salt)) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    //user.setPassword(resultSet.getString("password"));
                    user.setNom(resultSet.getString("nom"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setRole(user.role.valueOf(resultSet.getString("role")));
                    return user; // User found with the given email, password, and role
                }
            }
            return null; // No user found with the given email, password, and role
        }
    }
    public boolean emailExists(String email){
        String query = "SELECT  COUNT(*) FROM user WHERE email = ?";
        try{ PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; //if count>0 email exists
                }
            }catch(SQLException e){
                System.err.println("error checking email existence "+e.getMessage());
            }
            return false;
    }
    public User findByEmail(String selectedUserEmail) throws SQLException {
        User user = null;
        String query = "SELECT  * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, selectedUserEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setAddress(rs.getString("address"));
                    user.setNum_tel(rs.getString("num_tel"));
                    user.setDateJoined(rs.getDate("dateJoined"));
                    // Set other user properties as needed
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return user;
    }
}