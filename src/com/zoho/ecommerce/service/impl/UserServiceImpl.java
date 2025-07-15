package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.UserController;
import src.com.zoho.ecommerce.exception.UserAddException;
import src.com.zoho.ecommerce.exception.UserUpdateException;
import src.com.zoho.ecommerce.model.Customer;
import src.com.zoho.ecommerce.model.Seller;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.GlobalScanner;
import src.com.zoho.ecommerce.util.Validation;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserServiceImpl    {
    private final User loggedInUser ;
    private final Scanner sc = GlobalScanner.getScanner();
    private final UserController userController = new UserController();

    private final int CUSTOMER =1;
    private final int SELLER   =2;

    public UserServiceImpl(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    public void update()  {
        Validation check = new Validation();
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
                            throw new UserUpdateException("❌ Invalid choice , no updates made.");
                        }
                    }
                    case 0 -> {
                        System.out.println("👋 Exiting Update Menu.");
                        return;
                    }
                    default -> throw new UserUpdateException("❌ Invalid choice, no updates made.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid Input. Please enter a valid number.");
                sc.nextLine();
            } catch (UserUpdateException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    public User add() {
        Validation check = new Validation();
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
                    throw new UserAddException("❌ Invalid User Type. Please enter 1 for Client or 2 for Seller.");
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (UserAddException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // check duplicate Email exists
    private  String isEmailExists(String email, Validation check, User loggedInUser) {
        while (userController.isMailExists(email,loggedInUser)) {
            System.out.println("❌ Email already exists. Please try again with a different email.");
            email = check.email("📧 Enter a valid email address: ");
        }
        return email;
    }
    // checking duplicate Phone number exists
    private  String isPhoneExists(String phone, Validation check, User loggedInUser) {
        while (userController.isPhoneExists(phone, loggedInUser)) {

            System.out.println("❌ Phone number already exists. Please try again with a different number.");
            phone = check.phone("📱 Enter a valid phone number: ");
        }
        return phone;
    }
}
