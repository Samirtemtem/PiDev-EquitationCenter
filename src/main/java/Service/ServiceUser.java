package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection connection = Datasource.getConn();

    public ServiceUser() {
    }

    @Override
    public void add(User user) throws SQLException {
        String query = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getRole().toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, password = ?, name = ?, role = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getRole().toString());
            statement.setInt(5, user.getId());
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
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
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
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                users.add(user);
            }
        }
        return users;
    }

    public User login(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                return user; // User found with the given email, password, and role
            }
        }
        return null; // No user found with the given email, password, and role
    }
}