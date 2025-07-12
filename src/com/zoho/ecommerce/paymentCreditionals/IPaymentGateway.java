package src.com.zoho.ecommerce.paymentCreditionals;

public interface IPaymentGateway {

    void processPayment();

    String generateTransactionId();

    String confirmPayment(String transactionId);
}
