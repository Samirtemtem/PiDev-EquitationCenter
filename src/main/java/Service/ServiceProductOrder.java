package Service;

import Entities.ProductOrder;
import Entities.Product;
import Utils.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProductOrder implements IService<ProductOrder> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(ProductOrder productOrder) throws SQLException {
        String query = "INSERT INTO product_order (id, price, qty, status, total_price, product_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productOrder.getId());
            stmt.setFloat(2, productOrder.getPrice());
            stmt.setInt(3, productOrder.getQty());
            stmt.setString(4, productOrder.getStatus());
            stmt.setFloat(5, productOrder.getTotalPrice());
            stmt.setInt(6, productOrder.getProduct().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(ProductOrder productOrder) throws SQLException {
        String query = "UPDATE product_order SET price = ?, qty = ?, status = ?, total_price = ?, product_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, productOrder.getPrice());
            stmt.setInt(2, productOrder.getQty());
            stmt.setString(3, productOrder.getStatus());
            stmt.setFloat(4, productOrder.getTotalPrice());
            stmt.setInt(5, productOrder.getProduct().getId());
            stmt.setInt(6, productOrder.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(ProductOrder productOrder) throws SQLException {
        String query = "DELETE FROM product_order WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productOrder.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public ProductOrder findById(int id) throws SQLException {
        String query = "SELECT * FROM product_order WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createProductOrderFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ProductOrder> ReadAll() throws SQLException {
        List<ProductOrder> productOrders = new ArrayList<>();
        String query = "SELECT * FROM product_order";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductOrder productOrder = createProductOrderFromResultSet(rs);
                productOrders.add(productOrder);
            }
        }
        return productOrders;
    }

    private ProductOrder createProductOrderFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        float price = rs.getFloat("price");
        int qty = rs.getInt("qty");
        String status = rs.getString("status");
        int productId = rs.getInt("product_id");

        // Assuming you have a method to retrieve the associated Product object based on the product ID
        Product product = getProductById(productId);

        return new ProductOrder(id, price, qty, status, product);
    }

    private Product getProductById(int id) throws SQLException {
       ServiceProduct s = new ServiceProduct();

        return s.findById(id);
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}