package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.CategoryController;
import src.com.zoho.ecommerce.exception.CategoryOperationException;
import src.com.zoho.ecommerce.model.Category;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.GlobalScanner;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CategoryView   {
    private final User loggedInUser;
    private final CategoryController categoryController = new CategoryController();
    private  final  Scanner sc  = GlobalScanner.getScanner();
    private final int CLIENT = 1;
    private final int SELLER = 2;

    public CategoryView(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void view()throws CategoryOperationException {
        try {
            if (categoryController.isCategoryEmpty()) {
                throw new CategoryOperationException("📂 No categories available. Please add a category first.");
            }
            if (loggedInUser.getRole() == CLIENT) {
                while (true) {
                    System.out.println("1. View Categories BY Products\n2. View All Categories\n0. Exit");
                    System.out.println("🔢 Enter your choice:");
                    try {
                        int choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 1 -> viewCategoryForProducts(sc);
                            case 2 -> viewAllCategories();
                            case 0 -> {
                                System.out.println("🚪 Exiting Category View.");
                                return;
                            }
                            default -> System.out.println("❌ Invalid choice. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Invalid input. Please enter a valid number.");
                        sc.nextLine(); // clear invalid input
                    }
                }
            } else {
                viewAllCategories();
            }
        } catch (CategoryOperationException e) {
            throw e;
        }
    }

    // help methods ->for view all categories only
    public void viewAllCategories() throws CategoryOperationException {
        try {
            System.out.println("📋 Available Categories:");
            for (int i = 0; i < categoryController.getCategories().size(); i++) {
                System.out.println((i + 1) + ". " + categoryController.getCategories().get(i).getName());
            }
        } catch (Exception e) {
            throw new CategoryOperationException("Failed to view all categories.", e);
        }
    }

    // help methods -> for view all categories with products in a category
    public boolean viewCategoryForProducts(Scanner sc) throws CategoryOperationException {
        try {
            Category category = getCategory(sc);
            if (category == null) {
                System.out.println("❌ No categories available to view products.");
                return false;
            }
            if (category.getProduct().isEmpty()) {
                System.out.println("❌ No products available in this category.");
                return false;
            } else {
                System.out.println("📦 Products in Category: " + category.getName());
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                for (int i = 0; i < category.getProduct().size(); i++) {
                    System.out.println((i + 1) + ". " + category.getProduct().get(i));
                }
            }
            return true;
        } catch (Exception e) {
            throw new CategoryOperationException("Failed to view category products.", e);
        }
    }

    // help methods ;
    //->  for update() and delete()  common logic  ( return the selected categories for update or delete)
    //->  for add() product before   choosing category(return the selected category to Product helper)
    //->  for viewAllTheCategoryProduct its return the category to viewCategoryForProducts()

    public Category getCategory(Scanner sc) throws CategoryOperationException {
        try {
            if (categoryController.isCategoryEmpty()) {
                return null;
            }
            viewAllCategories();// display all categories
            int categoryIndex;
            do {
                System.out.println("🔢 Select a category by entering the corresponding number:");
                categoryIndex = sc.nextInt() - 1;
                sc.nextLine();
            } while (categoryIndex < 0 || categoryIndex >= categoryController.getCategories().size());

            return categoryController.getCategories().get(categoryIndex);
        } catch (Exception e) {
            throw new CategoryOperationException("Failed to get category.", e);
        }
    }
}
