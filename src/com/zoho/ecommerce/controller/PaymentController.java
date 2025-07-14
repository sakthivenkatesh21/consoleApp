package src.com.zoho.ecommerce.controller;

import src.com.zoho.ecommerce.payment.Gpay;
import src.com.zoho.ecommerce.payment.MayPay;
import src.com.zoho.ecommerce.payment.Paytm;
import src.com.zoho.ecommerce.interfaceController.IPaymentGateway;
import src.com.zoho.ecommerce.payment.PaymentProcessing;

public class PaymentController {
   ;

// Method overloaded to handle different payment methods
    public  String pay(double amount, String paymentMethod, String credentials) {
        return switch (paymentMethod) {
            case "GPay" -> 
                 process(new Gpay(amount, paymentMethod, credentials));
            case "Paytm" ->
                 process(new Paytm(amount, paymentMethod, credentials));
            default->
                 null;
        };
    }
// common logic for pay method to process payment
    private  String process(IPaymentGateway paymentGateway) {
        PaymentProcessing paymentProcessing = new PaymentProcessing(paymentGateway);
        return paymentProcessing.processPayment();
    }

    public  String pay(double amount, String paymentMethod, String credentials, String viaMode) {
        return switch (viaMode) {
            case "Upi" -> 
                process(new MayPay(amount, viaMode, credentials));
            case "NetBanking" -> 
                process(new MayPay(amount, viaMode, credentials));
            default -> 
                null;
        }; 
    }
}
