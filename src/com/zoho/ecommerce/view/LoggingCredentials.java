package com.zoho.ecommerce.view;

import java.util.Scanner;
import com.zoho.ecommerce.controller.SignIn;
import com.zoho.ecommerce.model.User;

public class LoggingCredentials {
    private User logInUser;
    private final Scanner sc = GlobalScanner.getScanner();

    public void logIn(Navigation navigation) {
        System.out.println("===================================");
        System.out.println("          🚪 LOGIN PAGE 🚪         ");
        System.out.println("===================================");
        System.out.println("Please enter your credentials below:");
        System.out.print("📧 Email: ");
        String email = sc.nextLine();
        System.out.print("🔒 Password: ");
        String password = sc.nextLine();
        System.out.println("===================================");

        logInUser = SignIn.validateLogIn(email, password);

        sigIn(navigation);
    }

    public void signUp(Navigation navigation) {
        logInUser  = new UserService(logInUser).add();
        if (logInUser != null) {
            sigIn(navigation);
        } else {
            System.out.println("User creation failed. Please try again.");
        }
    }

    private void sigIn( Navigation navigation) {
        if (logInUser != null) {
            navigation.showMenu(logInUser);
        } else {
            System.out.println("Invalid Email or Password");
            System.out.println("Please try again");
        }
    }
}
