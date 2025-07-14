package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.PaymentController;
import src.com.zoho.ecommerce.service.GlobalScanner;
import java.util.Scanner;

public class PaymentServiceImpl {
    private final Scanner sc = GlobalScanner.getScanner();
    private final PaymentController paymentController = new PaymentController();

    public String paytm(double amount) {
        System.out.println("🔄 Processing payment through Paytm...");
        System.out.println("📱 Enter your Paytm Wallet ID:");
        String walletId = sc.nextLine();
        return paymentController.pay(amount, "Paytm", walletId);
    }

    public String gPay(double amount) {
        System.out.println("🔄 Processing payment through GPay...");
        System.out.println("📱 Enter your GPay ID:");
        String gpayId = sc.nextLine();
        return paymentController.pay(amount, "GPay", gpayId);
    }

    public String upiId(double amount) {
        System.out.println("📱 Enter your UPI ID:");
        String upiId = sc.nextLine();
        return paymentController.pay(amount, "MAYPAY", upiId, "Upi");
    }

    public String netBanking(double amount) {
        System.out.println("🏦 Enter your bank details for NetBanking ID:");
        String netBanking = sc.nextLine();
        return paymentController.pay(amount, "MAYPAY", netBanking, "NetBanking");
    }
}

