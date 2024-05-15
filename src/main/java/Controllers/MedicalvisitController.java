package Controllers;

import Entities.Horse;
import Entities.MedicalVisit;
import Service.MedicalVisitService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class MedicalvisitController implements Initializable {

    @FXML
    private TableView<MedicalVisit> tableView;

    @FXML
    private TableColumn<MedicalVisit, Integer> id;

    @FXML
    private TableColumn<MedicalVisit, Integer> idHorseColumn;

    @FXML
    private TableColumn<MedicalVisit, Integer> idVetColumn;

    @FXML
    private TableColumn<MedicalVisit, String> descriptionColumn;

    @FXML
    private TableColumn<MedicalVisit, Date> visitDateColumn;

    @FXML
    private TextField search_tv;

    private MedicalVisitService medicalVisitService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicalVisitService = new MedicalVisitService();
        initializeColumns();
        loadMedicalVisits();
    }

    private void initializeColumns() {
        initializeTableColumns();
    }


    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<MedicalVisit, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MedicalVisit, Integer> idHorseColumn = new TableColumn<>("ID Horse");
        idHorseColumn.setCellValueFactory(new PropertyValueFactory<>("idHorse"));

        TableColumn<MedicalVisit, Integer> idVetColumn = new TableColumn<>("ID Vet");
        idVetColumn.setCellValueFactory(new PropertyValueFactory<>("idVet"));

        TableColumn<MedicalVisit, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<MedicalVisit, Date> visitDateColumn = new TableColumn<>("Visit Date");
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));


        TableColumn<MedicalVisit, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        tableView.getColumns().addAll(idColumn, idHorseColumn, idVetColumn, descriptionColumn, visitDateColumn, actionColumn);
    }

    @FXML
    private void pdf (javafx.scene.input.MouseEvent event) throws SQLException {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des visites médicales
            List<MedicalVisit> medicalVisitsList = tableView.getItems();

            try {
                // Créer le document PDF
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Titre du document
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD, BaseColor.DARK_GRAY);
                Paragraph title = new Paragraph("Liste des visites médicales", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                // Créer une table pour afficher les visites médicales
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // Ajouter les en-têtes de colonnes
                String[] headers = {"ID", "ID Horse", "ID Vet", "Description", "Visit Date"};
                Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                // Ajouter les données des visites médicales
                for (MedicalVisit visit : medicalVisitsList) {
                    table.addCell(String.valueOf(visit.getId()));
                    table.addCell(String.valueOf(visit.getIdHorse()));
                    table.addCell(String.valueOf(visit.getIdVet()));
                    table.addCell(visit.getDescription());
                    table.addCell(visit.getVisitDate().toString());
                }

                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private Callback<TableColumn<MedicalVisit, Void>, TableCell<MedicalVisit, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<MedicalVisit, Void>, TableCell<MedicalVisit, Void>>() {
            @Override
            public TableCell<MedicalVisit, Void> call(final TableColumn<MedicalVisit, Void> param) {
                final TableCell<MedicalVisit, Void> cell = new TableCell<MedicalVisit, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        modifyButton.setOnAction((ActionEvent event) -> {
                            MedicalVisit medicalVisit = getTableView().getItems().get(getIndex());
                            if (medicalVisit != null) {

                                RouterController.navigate("/fxml/Vet/modifyMedicalVisit.fxml", medicalVisit.getId());
                                System.out.println("Modify action for MedicalVisit with ID: " + medicalVisit.getId());
                            } else {
                                System.err.println("No MedicalVisit selected.");
                            }
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            MedicalVisit medicalVisit = getTableView().getItems().get(getIndex());
                            if (medicalVisit != null) {

                                try {
                                    medicalVisitService.delete(medicalVisit);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println("Delete action for MedicalVisit with ID: " + medicalVisit.getId());

                                tableView.getItems().remove(medicalVisit);
                            } else {
                                System.err.println("No MedicalVisit selected.");
                            }
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton); // Add buttons to HBox

                            modifyButton.setFocusTraversable(false);
                            deleteButton.setFocusTraversable(false);

                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }







    private void loadMedicalVisits() {
        try {
            List<MedicalVisit> medicalVisits = medicalVisitService.ReadAll();
            tableView.getItems().addAll(medicalVisits);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void gotoAjouter(Event event)
    {
        RouterController.navigate("/fxml/Vet/AddMedicalVisit.fxml");
    }
    public void addVisit(Event actionEvent)
    {

    }

    public void gohome(ActionEvent actionEvent) {
        RouterController r= new RouterController();
        r.navigate ("/fxml/Vet/VetDashboard.fxml");
    }
    public void returnTo(Event actionEvent){}

}
