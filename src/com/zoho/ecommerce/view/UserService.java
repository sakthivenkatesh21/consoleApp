package com.zoho.ecommerce.view;

import com.zoho.ecommerce.controller.UserController;
import com.zoho.ecommerce.interfaceController.Editable;
import com.zoho.ecommerce.interfaceController.Execute;
import com.zoho.ecommerce.interfaceController.Viewable;
import com.zoho.ecommerce.model.Customer;
import com.zoho.ecommerce.model.Seller;
import com.zoho.ecommerce.model.User;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService implements Execute, Editable, Viewable {
    private final User loggedInUser ;
    private final Scanner sc = GlobalScanner.getScanner();
    private final UserController userController = new UserController();

    private final int CUSTOMER =1;
    private final int SELLER   =2;

    public UserService(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    @Override
    public void operation() {
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
                    case 1 -> view();
                    case 2 -> update();
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

    @Override
    public void view() {
        String role = loggedInUser.getRole() == CUSTOMER ? "👤 Customer" : "🏢 Seller";
        System.out.println("""
                ╔═══════════════════════════════════════════════════╗
                |                📋 USER INFORMATION                |  
                ╚═══════════════════════════════════════════════════╝
                """);

        System.out.printf("🔑 Role              : %-30s%n", role);
        System.out.printf("🧑 Name              : %-30s%n", loggedInUser.getName());
        System.out.printf("📧 Email             : %-30s%n", loggedInUser.getEmail());
        System.out.printf("📱 Phone Number      : %-30s%n", loggedInUser.getPhone());
        System.out.printf("🏠 Address           : %-30s%n", (loggedInUser.getRole() == CUSTOMER ? ((Customer) loggedInUser).getAddress() : ((Seller) loggedInUser).getCompanyAddress()));
        // System.out.printf("🆔 User ID           : %-30s%n", loggedInUser.getId());

        if (loggedInUser.getRole() == SELLER) {
            Seller seller = (Seller) loggedInUser;
            System.out.printf("💰 Profit Earned     : ₹%-29.2f%n", seller.getProfit());
            System.out.printf("📦 Products Sold     : %-30d%n", seller.getSoldItem());
        }

        System.out.println("═════════════════════════════════════════════════════════");
    }

    @Override
    public void update() {
        ValidationService check = new ValidationService();
        while (true) {
            System.out.println("""
                    ╔═══════════════════════════════════════════════════╗
                    |           ✏️ UPDATE USER INFORMATION MENU          |
                    ╚═══════════════════════════════════════════════════╝
                    1️⃣  🧑  Update Name
                    2️⃣  📱  Update Phone Number
                    3️⃣  📧  Update Email
                    4️⃣  🔒  Update Password
                    5️⃣  🚻  Update Gender""");
            if (loggedInUser.getRole() == CUSTOMER) {
                System.out.println("6️⃣  🏠  Update Address (Customer)");
            } else if (loggedInUser.getRole() == SELLER) {
                System.out.println("""
                         6️⃣  🏢  Update Company Name (Seller)
                         7️⃣  📍  Update Company Address (Seller)""");
            }
            System.out.println("""
                   0️⃣  ❌  Exit Update Menu
                    ════════════════════════════════════════════════════
                    """);
            System.out.print("👉 Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        loggedInUser.setName(check.name("🧑 Enter new Name: "));
                        System.out.println("✅ Name updated successfully." + loggedInUser.getName());
                    }
                    case 2 -> {
                        loggedInUser.setPhone(isPhoneExists(check.phone("📱 Enter new phone number: "), check,loggedInUser));
                        System.out.println("✅ Phone number updated successfully." + loggedInUser.getPhone());
                    }
                    case 3 -> {
                        loggedInUser.setEmail(isEmailExists(check.email("📧 Enter new email: "), check,loggedInUser));
                        System.out.println("✅ Email updated successfully." + loggedInUser.getEmail());
                    }
                    case 4 -> {
                        String newPass =check.password("🔒 Enter new password: ");
                        while(newPass.equals(loggedInUser.getPassword())) {
                            System.out.println("❌ New password cannot be the same as the old one. Please enter a different password.");
                            newPass = check.password("🔒 Enter new password: ");
                        }
                        loggedInUser.setPassword(newPass);
                        System.out.println("✅ Password updated successfully.");
                    }
                    case 5 -> {
                        loggedInUser.setGender(check.gender("🚻 Enter new gender: "));
                        System.out.println("✅ Gender updated successfully." + loggedInUser.getGender());
                    }
                    case 6 -> {
                        if (loggedInUser.getRole() == CUSTOMER) {
                            ((Customer) loggedInUser).setAddress(check.address("🏠 Enter new address: "));
                            System.out.println("✅ Address updated successfully." + ((Customer) loggedInUser).getAddress());
                        } else if (loggedInUser.getRole() == SELLER) {
                            ((Seller) loggedInUser).setCompany(check.name("🏢 Enter new company name: "));
                            System.out.println("✅ Company name updated successfully." + ((Seller) loggedInUser).getCompany());
                        }
                    }
                    case 7 -> {
                        if (loggedInUser.getRole() == SELLER) {
                            ((Seller) loggedInUser).setCompanyAddress(check.address("📍 Enter new company address: "));
                            System.out.println("✅ Company address updated successfully." + ((Seller) loggedInUser).getCompanyAddress());
                        } else {
                            System.out.println("❌ Invalid choice , no updates made.");
                        }
                    }
                    case 0 -> {
                        System.out.println("👋 Exiting Update Menu.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice, no updates made.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid Input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    public User add(){
        ValidationService check = new ValidationService();
        String name = check.name("🧑 Enter your Name :");
        String phone = isPhoneExists(check.phone("📱 Enter a valid Phone number: "), check,loggedInUser);
        String email = isEmailExists(check.email("📧 Enter a valid Email : "), check,loggedInUser);
        String password = check.password("🔒 Type your Password:");
        String gender = check.gender("🚻 Enter your Gender:");
        int  userType = 0;
        while(true) {
            try {
                System.out.println("👥 Are you Signing Up as Client or Seller?\n 1️⃣ Customer\n 2️⃣ Seller\n (Enter a Number)");
                userType = sc.nextInt();
                sc.nextLine();
                if (userType == CUSTOMER)
                    return userController.createUser(name, phone,email, password, gender, check.address("🏠 Enter Address:"));
                else if (userType == SELLER)
                    return userController.createUser(name, phone, email, password, gender, check.name("🏢 Enter a Company Name :"), check.address("📍 Enter a Company Address :"));
                else
                    System.out.println("❌ Invalid User Type. Please enter 1 for Client or 2 for Seller.");
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

// check duplicate Email exists
    private  String isEmailExists(String email, ValidationService check, User loggedInUser) {
        while (userController.isMailExists(email,loggedInUser)) {
            System.out.println("❌ Email already exists. Please try again with a different email.");
            email = check.email("📧 Enter a valid email address: ");
        }
        return email;
    }
// checking duplicate Phone number exists
    private  String isPhoneExists(String phone, ValidationService check, User loggedInUser) {
        while (userController.isPhoneExists(phone, loggedInUser)) {
            
            System.out.println("❌ Phone number already exists. Please try again with a different number.");
            phone = check.phone("📱 Enter a valid phone number: ");
        }
        return phone;
    }
}
