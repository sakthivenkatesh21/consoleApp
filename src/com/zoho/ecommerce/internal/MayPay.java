package com.zoho.ecommerce.internal;

import com.zoho.ecommerce.paymentCreditionals.IPaymentGateway;

public class MayPay implements IPaymentGateway {

    private final double amount;
    private final String paymentMethod;
    private final String credentials;

    public MayPay(double amount, String paymentMethod, String credentials) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.credentials = credentials;
    }

    @Override
    public void processPayment() {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("MayPay: Invalid amount.");
            }
            if (paymentMethod == null || paymentMethod.isEmpty()) {
                throw new IllegalArgumentException("MayPay: Payment method required.");
            }
            if (credentials == null || credentials.isEmpty()) {
                throw new IllegalArgumentException("MayPay: Credentials (card/MayPay ID) required.");
            }

            switch (paymentMethod.toUpperCase()) {
                
                case "UPI"->
                    System.out.println("Processing MayPay UPI payment of ₹" + amount + " from MayPay ID: " + credentials);
                    

                case "NETBANKING"->
                    System.out.println("Processing MayPay NetBanking payment of ₹" + amount + " via bank: " + credentials);
                    

                default->
                    throw new IllegalArgumentException(paymentMethod + ": Unsupported payment method: " + paymentMethod);
            }
            System.out.println("✅ " + paymentMethod + " payment processed successfully.");
            Thread.sleep(2000);
        } catch (IllegalArgumentException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                System.out.println("Payment processing interrupted.");
            } else {
                System.out.println(paymentMethod + " error: " + e.getMessage());
            }
        }
          
    }

    @Override
    public String generateTransactionId() {
        return paymentMethod + "-" + System.nanoTime();
    }

    @Override
    public String confirmPayment(String transactionId) {
        if (transactionId == null || transactionId.isEmpty()) {
            System.out.println(paymentMethod + ": Invalid transaction ID.");
            return null;
        }
        System.out.println("✅ " + paymentMethod + " payment confirmed.");
        return  transactionId;
    }
}
