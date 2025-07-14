package src.com.zoho.ecommerce.payment;

import src.com.zoho.ecommerce.interfaceController.IPaymentGateway;

public class PaymentProcessing {
    private final IPaymentGateway iPaymentGateway;

    public PaymentProcessing(IPaymentGateway iPaymentGateway) {
        this.iPaymentGateway = iPaymentGateway;
    }

//    public IPaymentGateway getIPaymentGateway() {
//        return iPaymentGateway;
//    }

    public String processPayment() {
        iPaymentGateway.processPayment();
        return iPaymentGateway.confirmPayment(iPaymentGateway.generateTransactionId());
    }
}
