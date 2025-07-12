package src.com.zoho.ecommerce.view;

import java.util.InputMismatchException;
import java.util.Scanner;
import src.com.zoho.ecommerce.controller.PaymentController;

public class PaymentService {
    private  final Scanner sc =  GlobalScanner.getScanner();

    public  String paymentProcess( double amount) {
        System.out.println("💰 Your total amount is: " + amount);
        System.out.println("💳 Payment options available: \n 1. Paytm 🟢 \n 2. GPay 🔵 \n 3. MayPay 🟡");
        
        try {
            int paymentOption = sc.nextInt();
            sc.nextLine();
            switch (paymentOption) {
                case 1 -> {
                    System.out.println("🔄 Processing payment through Paytm...");
                    System.out.println("📱 Enter your Paytm Wallet ID:");
                    String walletId = sc.nextLine();
                    return PaymentController.pay(amount, "Paytm", walletId);
                }
                case 2 -> {
                    System.out.println("🔄 Processing payment through GPay...");
                    System.out.println("📱 Enter your GPay ID:");
                    String gpayId = sc.nextLine();
                    return PaymentController.pay(amount, "GPay", gpayId);
                }
                case 3 -> {
                    return maybepay( amount);
                }
                default -> System.out.println("❌ Invalid payment option selected. Please try again.");
                
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            sc.nextLine();
        }
        catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    private  String maybepay( double amount ) {
        System.out.println("🔄 Processing payment through MayPay...");
        System.out.println("💳 Payment methods available: \n1. UPI 📱 \n2. NetBanking 🏦");
       
        try {
            int paymentMode = sc.nextInt();
            sc.nextLine();
            switch (paymentMode) {
                case 1 -> {
                    System.out.println("📱 Enter your UPI ID:");
                    String upiId = sc.nextLine();
                    return PaymentController.pay(amount, "MAYPAY", upiId, "Upi");
                }
                case 2 -> {
                    System.out.println("🏦 Enter your bank details for NetBanking ID:");
                    String netBanking = sc.nextLine();
                    return PaymentController.pay(amount, "MAYPAY", netBanking, "NetBanking");
                }
                default -> {
                    System.out.println("❌ Invalid payment mode selected. Please try again.");
   
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }
}
