package src.com.zoho.ecommerce.interfaceController;

public interface IPaymentGateway {

    void processPayment();

    String generateTransactionId();

    String confirmPayment(String transactionId);
}
