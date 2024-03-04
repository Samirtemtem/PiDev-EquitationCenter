package Controllers;

import Entities.User;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class SMSController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACef33a5a8f8fc5c4f49185924b3d4cfaf";
    public static final String AUTH_TOKEN = "f36ea2606f7cd92459039239cf0ec9a5";

    public static void Sms(User client) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21694854835"), // DEMO
                new com.twilio.type.PhoneNumber("+19192611686"),


                "Commande effectuee a un client : nom:" + client.getName() +"la commande est dispo").create();

        System.out.println(message.getSid());
    }
}