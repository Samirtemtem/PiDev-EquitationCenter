package Controllers;




import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;

import java.util.HashMap;
import java.util.Map;

public class PaymentApi {

    public static String pay(int f) throws StripeException {
        Stripe.apiKey ="sk_test_51MfmZCAyRwA2k3iVYHgxsElKg90nJP1n7s5xCZHH1pE2ZMNnllelyJGpfoXt5RaKKAFjdizlCONe6Qgt0nmZpXBy0068HpRI3i";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", f);
        params.put("currency", "usd");
        params.put("customer", "cus_NQdtcHsC8SKEPl");

        // Get the client's Payment Page URL

        Charge charge = Charge.create(params);
        System.out.println(charge);
        return charge.getReceiptUrl();



    }

}

