package Controllers;

import Entities.Activity;
import Entities.Product;
import Service.ServiceActivity;
import Service.ServiceProduct;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsListController implements Initializable {
    public GridPane ActivitiesListContainer;
    public Pane content_area;
    public GridPane ProductListContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("I GOT HERE");
        // Call your service to get the list of events
        ServiceProduct es=new ServiceProduct();
        List<Product> products;
        try {
            products = es.ReadAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Populate the GridPane with the events data
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < products.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/UserProductCard.fxml"));
                VBox oneProductCard = fxmlLoader.load();
                UserProductCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(products.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                ProductListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 10, 10, 5));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}