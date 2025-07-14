package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.impl.UserServiceImpl;
import src.com.zoho.ecommerce.view.UserView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService implements Execute {
    private final User loggedInUser ;
    private final Scanner sc = GlobalScanner.getScanner();

    public UserService(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    @Override
    public void operation() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(loggedInUser);
        while (true) {
            System.out.println("====================================");
            System.out.println("         🛠️ User Management Menu       ");
            System.out.println("====================================");
            System.out.println("1️⃣ View Info");
            System.out.println("2️⃣ Update Info");
            System.out.println("0️⃣ Exit");
            System.out.println("====================================");
            System.out.print("👉 Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> new UserView(loggedInUser).view();
                    case 2 -> userServiceImpl.update();
                    case 0 -> {
                        System.out.println("👋 Exiting User Management.");
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
