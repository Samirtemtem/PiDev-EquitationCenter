package Controllers;

import Entities.Activity;
import Entities.ActivitySession;
import Entities.UserActivity;
import Service.ServiceActivity;
import Service.ServiceActivitySession;
import Service.UserActivityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActivityDetails implements InitializableController {

    public Label activitySessionsLabel;
    @FXML
        private ImageView activityImage;

        @FXML
        private Label activityName;

        @FXML
        private Label activityDescription;

        @FXML
        private Label activityPrice;

    @FXML
    private Button subscribeButton; // New Button for Subscription

        private Activity activity;
        private List<ActivitySession> activitySession;
        private ServiceActivitySession serviceActivitySession=new ServiceActivitySession();
    private Integer activityId;

    public void initialize() throws SQLException {
            // Populate the activity details
            if (activity != null) {
                activityName.setText("Titre: "+activity.getTitle());
                activityDescription.setText("Description: "+activity.getDescription());
                activityPrice.setText("Prix: " + activity.getPrice());

                try {
                    Image image = createImageFromBytes(activity.getImageData());
                    activityImage.setImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle image conversion error
                }
            }
            loadActivitySessions();

        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        private Image createImageFromBytes(byte[] imageData) throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            return new Image(bis);
        }

        @FXML
        private void goBack() {
            // Code to go back to the previous interface
            // You can use the FXMLLoader to load the previous FXML
        }


    public void goToNavigate(ActionEvent actionEvent) {
    }

    public void goToActivities(MouseEvent mouseEvent) {
    }

    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/Login/Login.fxml");
    }

    public void initData(int activity) throws SQLException {
            ServiceActivity sa=new ServiceActivity();
            this.activity=sa.findById(activity);
            initialize();
    }
    private void loadActivitySessions() {
        if (activity != null) {
            List<ActivitySession> activitySessions = serviceActivitySession.readAllActivitySessions(activity.getId());
            StringBuilder sessionsText = new StringBuilder();

            for (ActivitySession session : activitySessions) {
                sessionsText.append(session.getWeekdayAsString())
                        .append(", De ").append(session.getStartTime())
                        .append(" à ").append(session.getEndTime())
                        .append("\n");
            }
            System.out.println(sessionsText);
            System.out.println(activitySessions);

            activitySessionsLabel.setText("Séances:\n" + sessionsText.toString());
        }
    }

    public void SubscribeToActivity(ActionEvent actionEvent) throws SQLException {
            int userid=GuiLoginController.user.getId();
            if(userid==0)
                userid=1;

        UserActivityService sas=new UserActivityService();
        int found = sas.findbyuserIdandActivityId(userid,activity.getId());
        if(found==1)
        {
            System.out.println("Activityuesr already found");
            showInformationAlert("Vous etes déjà abonnée a cette activitée, Veuillez trouver une autre activité", "Erreur");
            return;
        }
        UserActivity userActivity=new UserActivity();
        userActivity.setUserId(userid);
        userActivity.setActivityId(activity.getId());
        userActivity.setFeedback("");
        userActivity.setStars(0);
        System.out.println(userActivity);
        sas.add(userActivity);
        System.out.println("User activity Added successfully");
        showInformationAlert("Vous etes maintenant abonnée a cette activitée, tu dois payer vers notre centre d'equitation", "Information");

    }
    public void showInformationAlert(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void init(Integer Id) {
        this.activityId=Id;
        try{
            initData(Id);
        }catch(SQLException e)
        {
            System.out.println(e.getErrorCode());
        }
    }
}
