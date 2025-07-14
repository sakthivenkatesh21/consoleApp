package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.impl.ShoppingCardImpl;
import src.com.zoho.ecommerce.view.ProductView;
import src.com.zoho.ecommerce.view.CardView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingCardService implements Execute {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;

    public ShoppingCardService(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void operation() {
        ShoppingCardImpl wishlistHandlerImpl = new ShoppingCardImpl(loggedInUser);
        while (true) {
            System.out.println("========================================");
            System.out.println("         🌟 Wish List Management 🌟         ");
            System.out.println("========================================");
            System.out.println("1️⃣ Add to Wish List");
            System.out.println("2️⃣ View Wish List");
            System.out.println("3️⃣ Update Wish List");
            System.out.println("4️⃣ Delete from Wish List");
            System.out.println("0️⃣ Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> new ProductView( loggedInUser).view();
                    case 2 -> new CardView(loggedInUser).view();
                    case 3 -> wishlistHandlerImpl.update();
                    case 4 -> wishlistHandlerImpl.delete();
                    case 0 -> {
                        System.out.println("🚪 Exiting Wish List Management.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice, please try again.");
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
