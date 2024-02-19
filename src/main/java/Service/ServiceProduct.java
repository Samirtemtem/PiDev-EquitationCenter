package Service;

import Entities.Product;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IService<Product>{


        private Connection conn = Datasource.getConn();

        @Override
        public void add(Product product) throws SQLException {
            String query = "INSERT INTO product (id, name, price, description, stock_qty) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, product.getId());
                stmt.setString(2, product.getName());
                stmt.setFloat(3, product.getPrice());
                stmt.setString(4, product.getDescription());
                stmt.setInt(5, product.getStockQty());
                stmt.executeUpdate();
            }
        }

        @Override
        public void update(Product product) throws SQLException {
            String query = "UPDATE product SET name = ?, price = ?, description = ?, stock_qty = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, product.getName());
                stmt.setFloat(2, product.getPrice());
                stmt.setString(3, product.getDescription());
                stmt.setInt(4, product.getStockQty());
                stmt.setInt(5, product.getId());
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
            int id = rs.getInt("id");
            String name = rs.getString("name");
            float price = rs.getFloat("price");
            String description = rs.getString("description");
            int stockQty = rs.getInt("stock_qty");

            return new Product(id, name, price, description, stockQty);
        }

        public void closeConnection() throws SQLException {
            if (conn != null) {
                conn.close();
            }
        }
    }