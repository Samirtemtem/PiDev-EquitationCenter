package Controllers;

import Entities.Vet;
import Service.ServiceVet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class VetCrudController {

    @FXML
    private TableView<Vet> tableView; // Assuming you've set this ID in your FXML file

    private final ServiceVet serviceVet = new ServiceVet();

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTableColumns();
        updateVetList();
        // Call method to retrieve and display vets
    }

    public void updateVetList() {
        try {
            // Retrieve all vets from the service
            List<Vet> vets = serviceVet.ReadAll();

            // Clear existing items in the TableView
            tableView.getItems().clear();

            // Add retrieved vets to the TableView
            tableView.getItems().addAll(vets);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        // Clear existing columns
        tableView.getColumns().clear();

        // Add columns for vet properties
        TableColumn<Vet, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vet, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Vet, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Vet, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Vet, String> dateJoinedColumn = new TableColumn<>("Date Joined");
        dateJoinedColumn.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));

        // Add columns to TableView
        tableView.getColumns().addAll(idColumn, nameColumn, emailColumn, passwordColumn, dateJoinedColumn);
    }

    public void searchquery(KeyEvent keyEvent) {
        // Implement search functionality if needed
    }

    public void gotoAjouter(ActionEvent actionEvent) {
        // Implement navigation to add vet view if needed
    }
}
