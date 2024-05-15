package Service;

import Entities.Post;
import Service.IService;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePost implements IService<Post> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Post post) throws SQLException {
        String query = "INSERT INTO post (Date, Description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(post.getDate().getTime()));
            stmt.setString(2, post.getDescription());
          //  stmt.setInt(3, post.getImage().getId());
            stmt.executeUpdate();

            // Retrieve the generated ID if needed
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating post failed, no ID obtained.");
            }
        }
    }

    @Override
    public void update(Post post) throws SQLException {
        String query = "UPDATE post SET Date = ?, Description = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(post.getDate().getTime()));
            stmt.setString(2, post.getDescription());
            stmt.setInt(3, post.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Post post) throws SQLException {
        String query = "DELETE FROM post WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, post.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Post findById(int id) throws SQLException {
        String query = "SELECT * FROM post WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createPostFromResultSet(rs);
                }
            }
        }
        // If no post is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Post> ReadAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM post";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Post post = createPostFromResultSet(rs);
                posts.add(post);
            }
        }
        return posts;
    }

    private Post createPostFromResultSet(ResultSet rs) throws SQLException {
        // You need to implement the logic to create a Post object from the ResultSet
        int id = rs.getInt("id");
        Date date = rs.getDate("Date");
        String description = rs.getString("Description");
      //  int imageId = rs.getInt("ImageId");
        // You may need to fetch the image object based on the imageId from the database
        // Construct the Post object with the retrieved data and return it
        return new Post(id, date, description);
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
