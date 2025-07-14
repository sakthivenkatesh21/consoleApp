package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.service.impl.PaymentServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PaymentService {
    private  final Scanner sc =  GlobalScanner.getScanner();
    private  final PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();

    public  String paymentProcess( double amount) {
        System.out.println("💰 Your total amount is: " + amount);
        System.out.println("💳 Payment options available: \n 1. Paytm 🟢 \n 2. GPay 🔵 \n 3. MayPay 🟡");
        
        try {
            int paymentOption = sc.nextInt();
            sc.nextLine();
            switch (paymentOption) {
                case 1 -> {
                    return paymentServiceImpl.paytm(amount);
                }
                case 2 -> {
                    return paymentServiceImpl.gPay(amount);

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
                    return paymentServiceImpl.upiId(amount);

                }
                case 2 -> {
                    return paymentServiceImpl.netBanking(amount);

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
