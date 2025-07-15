package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public  class Navigation {

    private final Scanner sc ;
    private final LoggingCredentials credentials;
    private static Navigation getNavigation;
    private  final int CUSTOMER =1;
    private  final int SELLER   =2;
    // static block to feed some data
    static
    {
       new DefaultProductEntry();
    }
    private Navigation() {
        this.sc = GlobalScanner.getScanner();
        credentials = new LoggingCredentials();
    }
    // singleton  method   for Navigation
    public static Navigation getNavigation() {
        if (getNavigation == null) {
            getNavigation = new Navigation();
        }
        return getNavigation;
    }

    public void menu() {
        System.out.println("🌟 Welcome to E - Commerce 🌟");
        while (true) {
            try {
                System.out.println("=================================");
                System.out.println("       🌟 E - Commerce Menu 🌟    ");
                System.out.println("=================================");
                System.out.println("1. ✍️ Sign Up");
                System.out.println("2. 🔑 Sign In");
                System.out.println("3. ❌ Exit");
                System.out.println("=================================");
                System.out.print("👉 Please enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> credentials.signUp(this);
                    case 2 -> credentials.logIn(this);
                    case 3 -> GlobalScanner.exit(sc);
                    default -> System.out.println("Invalid Choice");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine(); 
            } catch (Exception e) {
                System.out.println("Error: An unexpected error occurred.");
            }
        }
    }
    // showing the menu for the role can Access
    public void showMenu(User loggedInUser){

        System.out.println("Welcome  " + loggedInUser.getName() + " to the E-commerce Platform");
        while (true) {
            System.out.println("1.Account\n2.Category\n3.Product\n4.Order");
            if (loggedInUser.getRole() == CUSTOMER) System.out.println("5.Card");
            System.out.println("0.Exit");
            System.out.println("Please enter your choice: ");
            try{
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> operation(new UserService(loggedInUser));
                    case 2 -> operation(new ProductCategoryService(loggedInUser));
                    case 3 -> operation(new ProductService(loggedInUser));
                    case 4 -> operation( new OrderService(loggedInUser));
                    case 5 ->{
                        if(loggedInUser.getRole()==CUSTOMER)
                            operation(new ShoppingCardService(loggedInUser));
                        else System.out.println("Invalid Choice !");
                    }
                    case 0 -> {
                        System.out.println("Existing From Customer Menu");
                        return;
                    }
                    default ->  System.out.println("Invalid Choice !");
                }
            }
            catch(InputMismatchException ime) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
            catch (Exception e) {
                System.out.println("Error : An unexpected error occurred." + e.getMessage());
            }
        }
    }

    private void operation(Execute execute) {
//        UserAccess userAccess = new UserAccess(execute);
        execute.operation();
    }

}
