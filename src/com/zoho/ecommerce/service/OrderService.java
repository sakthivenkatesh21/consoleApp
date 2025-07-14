package src.com.zoho.ecommerce.service;


import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.impl.OrderServiceImpl;
import src.com.zoho.ecommerce.view.OrderView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderService implements Execute {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;

    private final int CUSTOMER = 1;
    private final int SELLER = 2;

    public OrderService(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    @Override
    public void operation() {
        OrderView orderView = new OrderView(loggedInUser);

        while (true) {
            try {
                if (loggedInUser.getRole() == SELLER) {
                    System.out.println("\n==============================");
                    System.out.println("📦 1. View Orders");
                    System.out.println("🚪 0. Exit");
                    System.out.println("==============================");
                } else if (loggedInUser.getRole() == CUSTOMER) {
                    System.out.println("\n==============================");
                    System.out.println("🛒 1. Checkout");
                    System.out.println("📦 2. View Order");
                    System.out.println("🚪 0. Exit");
                    System.out.println("==============================");
                }
                System.out.print("👉 Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        if (loggedInUser.getRole() == SELLER) orderView.view();
                        if (loggedInUser.getRole() == CUSTOMER) new OrderServiceImpl(loggedInUser).checkout();
                    }
                    case 2 -> {
                        if (loggedInUser.getRole() == CUSTOMER) orderView.view();
                        else System.out.println("❌ Invalid choice. Please try again.");
                    }
                    case 0 -> {
                        System.out.println("🚪 Exiting Order Management.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
