package Service;

import Entities.ProductOrder;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProductOrder implements IService<ProductOrder> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(ProductOrder productOrder) throws SQLException {
        String query = "INSERT INTO product_order (Price, Qty, Status, Product_id, Total_price,id_client) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, productOrder.getPrice());
            stmt.setInt(2, productOrder.getQty());
            stmt.setString(3, productOrder.getStatus());
            stmt.setInt(4, productOrder.getProduct_id());
            stmt.setFloat(5, productOrder.getTotal_price());
            stmt.setInt(6, productOrder.getId_client());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(ProductOrder ProductOrder) throws SQLException {
        String query = "UPDATE product_order SET Price = ?, Qty = ?, Status = ?, Total_price = ?,product_id = ?,id_client = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, ProductOrder.getPrice() );
            stmt.setInt(2, ProductOrder.getQty());
            stmt.setString(3, ProductOrder.getStatus());
            stmt.setFloat(4, ProductOrder.getTotal_price());
            stmt.setInt(5,ProductOrder.getProduct_id());
            stmt.setInt(6, ProductOrder.getId());
            stmt.setInt(7, ProductOrder.getId_client());

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
        // If no product order is found, return null or throw an exception
        return null;
    }

    @Override
    public List<ProductOrder> ReadAll() throws SQLException {
        List<ProductOrder> productOrders = new ArrayList<>();
        String query = "SELECT * FROM product_order";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getFloat("Price"));
                System.out.println(rs.getInt(5));
                ProductOrder productOrder = createProductOrderFromResultSet(rs);
                productOrders.add(productOrder);
            }
        }
        return productOrders;
    }

    private ProductOrder createProductOrderFromResultSet(ResultSet rs) throws SQLException {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(rs.getInt("id"));
        productOrder.setPrice(rs.getFloat("Price"));
        productOrder.setQty(rs.getInt("Qty"));
        productOrder.setStatus(rs.getString("Status"));
        productOrder.setProduct_id(rs.getInt("Product_id"));
        productOrder.setTotal_price(rs.getFloat("Total_price"));
        productOrder.setId_client(rs.getInt("id_client"));
        return productOrder;
    }


    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
