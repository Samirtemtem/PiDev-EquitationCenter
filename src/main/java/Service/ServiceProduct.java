package Service;

import Entities.Product;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IService<Product> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Product product) throws SQLException {
        String query = "INSERT INTO product (Name, Price, Description, StockQty, BlobImage) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setFloat(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getStockQty());
            stmt.setBytes(5, product.getBlobImage()); // Assuming blobImage is byte array
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        String query = "UPDATE product SET Name = ?, Price = ?, Description = ?, StockQty = ?, BlobImage = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setFloat(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getStockQty());
            stmt.setBytes(5, product.getBlobImage());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Product product) throws SQLException {
        String query = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Product findById(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createProductFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> ReadAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = createProductFromResultSet(rs);
                products.add(product);
            }
        }
        return products;
    }

    private Product createProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("Name"));
        product.setPrice(rs.getFloat("Price"));
        product.setDescription(rs.getString("Description"));
        product.setStockQty(rs.getInt("StockQty"));
        product.setBlobImage(rs.getBytes("BlobImage"));
        return product;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
