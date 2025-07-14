package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.impl.ProductCategoryImpl;
import src.com.zoho.ecommerce.view.CategoryView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductCategoryService implements Execute {
        private final Scanner sc = GlobalScanner.getScanner();
        private final User loggedInUser;
        private final int CLIENT = 1;
        private final int SELLER = 2;

        public ProductCategoryService(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }


        @Override
        public void operation() {
            ProductCategoryImpl categoryServiceImpl = new ProductCategoryImpl(loggedInUser);
            CategoryView categoryView = new CategoryView(loggedInUser);
            System.out.println("🌟✨ Welcome to Category Management ✨🌟");
            System.out.println("👤    Name: " + loggedInUser.getName());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            while (true) {
                try {
                    if (loggedInUser.getRole() == CLIENT) {
                        categoryView.view();
                        return;
                    } else if (loggedInUser.getRole() == SELLER) {
                        System.out.println("1️⃣ Add Category \n2️⃣ View Categories \n3️⃣ Delete Category \n4️⃣ Update Category \n0️⃣ Exit");
                    }
                    System.out.println("🔢 Enter your choice:");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1 -> categoryServiceImpl.add();
                        case 2 -> categoryView.viewAllCategories();
                        case 3 -> categoryServiceImpl.delete();
                        case 4 -> categoryServiceImpl.update();
                        case 0 -> {
                            System.out.println("🚪 Exiting Category Management. Goodbye! 👋");
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Invalid input. Please enter a valid number.");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("❌ An unexpected error occurred: " + e.getMessage());
                    sc.nextLine();
                }
            }
        }

}

