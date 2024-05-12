package Service;

import Entities.Users;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<Users> {
    private Connection connection = Datasource.getConn();
   // PassSecurity ps =new PassSecurity();

    public ServiceUser() {
    }

    @Override
    public void add(Users user) throws SQLException {
      //  byte[] salt = ps.generateSalt();
        //hash the password withe salt
        if(emailExists(user.getEmail())){
            System.out.println("User with email"+user.getEmail()+"already exists.");
            return ;// Exit the method if email exists
        }

        String query = "INSERT INTO users (email,password, name,lastname,address,num_tel,dateJoined, roles,ImageData) VALUES (?,?, ?, ?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
           // String hashedPassword = ps.hashPassword(user.getPassword(), salt);
            statement.setString(2, user.getPassword());
          //  statement.setBytes(3, salt);
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getNum_tel());
            statement.setDate(7, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setString(8, user.getRoles().toString());
            statement.setBytes(9, user.getImageData()); // Set image data as byte array
            statement.executeUpdate();
        }
    }



    @Override
    public void update(Users user) throws SQLException {
        String query = "UPDATE users SET email = ?, password = ?, name = ?, lastname = ?,address = ?,num_tel = ?,dateJoined = ?,roles = ? ,ImageData = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getNum_tel());
            statement.setDate(7, new java.sql.Date(user.getDateJoined().getTime()));
            statement.setString(8, user.getRoles().toString());
            statement.setBytes(9, user.getImageData()); // Set image data as byte array
            statement.setInt(10, user.getId());
            statement.executeUpdate();
        }
    }


    @Override
    public void delete(Users user) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Users findById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
              Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRoles(resultSet.getString("roles"));
                return user;
            }
            return null; // No user found with the given ID
        }
    }

    @Override
    public List<Users> ReadAll() throws SQLException {
        List<Users> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAddress(resultSet.getString("address"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setDateJoined(resultSet.getDate("dateJoined"));
                user.setImageData(resultSet.getBytes("imageData"));
                user.setRoles(resultSet.getString("roles"));
                users.add(user);
            }
        }
        return users;
    }

    public Users login(String email, String password) throws SQLException {
        Users user = null;
        if (!emailExists(email)) {
            System.out.println("email doesn't exist");
            return null;
        }
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
          //   statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               // byte[] salt = resultSet.getBytes("salt");
             //   String hashedPassword = resultSet.getString("hash");
              //  if (ps.validatePassword(password, hashedPassword, salt)) {
                    user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastname"));
                //    user.setRoles(Users.Roles.valueOf(resultSet.getString("roles")));
                     user.setRoles(resultSet.getString("roles"));
                    return user; // User found with the given email, password, and role
                }
            }
            return null; // No user found with the given email, password, and role
        }


    public List<Users> ReadInstructors() throws SQLException {
        List<Users> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE Roles='ROLE_INSTRUCTOR'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                //user.setRoles(Users.Roles.valueOf(resultSet.getString("roles")));
              user.setRoles(resultSet.getString("roles"));
                users.add(user);
            }
        }
        return users;
    }

    public List<Users> ReadClients() throws SQLException {
        List<Users> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE Role='ROLE_CLIENT'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
              user.setRoles(resultSet.getString("roles"));
                //user.setRoles(Users.Roles.valueOf(resultSet.getString("roles")));
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
    public Users findByEmail(String selectedUserEmail) throws SQLException {
        Users user = null;
        String query = "SELECT  * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, selectedUserEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new Users();
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
