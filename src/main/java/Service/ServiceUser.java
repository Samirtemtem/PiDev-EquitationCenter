package Service;

import Entities.Role;
import Entities.User;
import Utils.Datasource;
import Utils.PassSecurity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class ServiceUser implements IService<User> {
    private Connection connection = Datasource.getConn();
    PassSecurity ps =new PassSecurity();

    public ServiceUser() {
    }

    @Override
    public void add(User user) throws SQLException {
        //hash the password withe salt
        if(emailExists(user.getEmail())){
            System.out.println("User with email"+user.getEmail()+"already exists.");
            return ;// Exit the method if email exists
        }

        String query = "INSERT INTO users (email, password, name,lastname,address,num_tel,dateJoined, roles,ImageData) VALUES (?,?, ?, ?, ?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(13));
            statement.setString(2, encryptedPassword);
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getNum_tel());
            statement.setDate(7, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setString(8, user.getRole().toString());
            statement.setBytes(9, user.getImageData()); // Set image data as byte array
            statement.executeUpdate();
        }
    }



    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, password = ?, name = ?, lastname = ?,address = ?,num_tel = ?,dateJoined = ?,roles = ? ,ImageData = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
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
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRole(resultSet.getString("roles"));
                return user;
            }
            return null; // No user found with the given ID
        }
    }

    @Override
    public List<User> ReadAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRole(resultSet.getString("roles"));
                users.add(user);
            }
        }
        return users;
    }

    public User login(String email, String password) throws SQLException {
        User user = null;
        if (!emailExists(email)) {
            System.out.println("email doesn't exist");
            return null;
        }
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            // statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastname"));
                    user.setRole(resultSet.getString("roles"));

                    if (BCrypt.checkpw(password,user.getPassword()))
                    return user; // User found with the given email, password, and role
                }
            }
            return null; // No user found with the given email, password, and role
        }

    public List<User> ReadInstructors() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE roles='\"[\\\"ROLE_CLIENT\\\"]\"'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRole(resultSet.getString("roles"));
                users.add(user);
            }
        }
        return users;
    }

    public List<User> ReadClients() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE Role='CLIENT'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRole(resultSet.getString("roles"));
                users.add(user);
            }
        }
        return users;
    }
    public boolean emailExists(String email){
        String query = "SELECT  COUNT(*) FROM users WHERE email = ?";
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
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastname"));
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