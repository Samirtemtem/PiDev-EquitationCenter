package Controllers;

import Entities.Product;
import Entities.User;
import Entities.ProductOrder;
import Service.ServiceProduct;
import Service.ServiceUser;
import Service.ServiceProductOrder;
import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiPaiementController implements Initializable {

    @FXML
    private TextField anneeExp;

    @FXML
    private TextField carte;

    @FXML
    private TextField cvc;

    @FXML
    private TextField moisExp;

    @FXML
    private Button Pay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Pay(ActionEvent event) throws StripeException, SQLException {
        ServiceProductOrder scom = new ServiceProductOrder();
        ProductOrder productOrder;
        ServiceUser sc = new ServiceUser();
        User client;

        if (isValidInput()) {
             float f = (float) product.getPrice()*100;
            int k = floatToInt(f);
            String url=PaymentApi.pay(k);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement");
            alert.setContentText("Paiement effectué avec succès, Votre Commande a été enregistré");
            alert.showAndWait();
            Stage stage = new Stage ();

            final WebView webView = new WebView();
            final WebEngine webEngine = webView.getEngine();
            webView.getEngine().load(url);

            // create scene
            //   stage.getIcons().add(new Image("/Images/logo.png"));
            stage.setTitle("localisation");
            Scene scene = new Scene(webView,1000,700, Color.web("#666970"));
            stage.setScene(scene);
            // show stage
            stage.show();
            SMSController.Sms(GuiLoginController.user);

        }

            int productId = product.getId();
            int clientId=GuiLoginController.user.getId();
            if(clientId==0)
                clientId=1;
            // Creating a product order
            productOrder = new ProductOrder();
            productOrder.setPrice(20.0f); // Replace with actual product price
            productOrder.setQty(1); // Replace with actual quantity
            productOrder.setStatus("Paid");
            productOrder.setProduct_id(productId);
            productOrder.setTotal_price(productOrder.getPrice() * productOrder.getQty());
            productOrder.setId_client(clientId);
        ServiceProduct p=new ServiceProduct();
        product.setStockQty(product.getStockQty()-1);
        p.update(product);
            scom.add(productOrder);

        }

    private boolean isValidInput() {
        if (!isValidVisaCardNo(carte.getText())) {
            showError("Numéro de carte invalide", "Veuillez entrer un numéro de carte Visa valide.");
            return false;
        }

        if (moisExp.getText().isEmpty() || !isNum(moisExp.getText()) || Integer.parseInt(moisExp.getText()) < 1 || Integer.parseInt(moisExp.getText()) > 12) {
            showError("Mois d'expiration invalide", "Veuillez entrer un mois d'expiration valide (entre 1 et 12).");
            return false;
        }

        if (anneeExp.getText().isEmpty() || !isNum(anneeExp.getText()) || Integer.parseInt(anneeExp.getText()) < LocalDate.now().getYear()) {
            showError("Année d'expiration invalide", "Veuillez entrer une année d'expiration valide.");
            return false;
        }

        if (cvc.getText().isEmpty() || !isNum(cvc.getText())) {
            showError("Code CVC invalide", "Veuillez entrer un code CVC numérique valide.");
            return false;
        }

        return true;
    }

    private boolean isValidVisaCardNo(String text) {
        String regex = "^4[0-9]{12}(?:[0-9]{3})?$";
        Pattern p = Pattern.compile(regex);
        CharSequence cs = text;
        Matcher m = p.matcher(cs);
        return m.matches();
    }

    public static boolean isNum(String str) {
        String expression = "\\d+";
        return str.matches(expression);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int floatToInt(float value) {
        return (int) value;
    }
    private Product product;
    public void init(Product p) {
        this.product=p;
        this.Pay.setText("Payer "+p.getPrice()+"dinars");
    }
}
