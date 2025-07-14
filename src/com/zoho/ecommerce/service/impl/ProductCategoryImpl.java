package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.CategoryController;
import src.com.zoho.ecommerce.model.Category;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.GlobalScanner;

import src.com.zoho.ecommerce.util.Validation;
import src.com.zoho.ecommerce.view.CategoryView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductCategoryImpl {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;
    private final CategoryController categoryController = new CategoryController();



    public ProductCategoryImpl(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void add() {
        Validation validationService = new Validation();
        String categoryName = validationService.name("📝 Enter the Category Name:");
        String categoryDescription = validationService.address("📝 Enter the Category Description:");
        if (categoryController.createCategory(categoryName, categoryDescription) != null) {
            System.out.println("✅ Category Created Successfully 🎉");
        } else {
            System.out.println("⚠️ Category with name '" + categoryName + "' already exists.");
        }
    }


    public void update() {
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("📂 No categories available to update");
            return;
        }
        System.out.println("✏️ You are about to update the category: " + category.getName());
        System.out.println("1.Category Name\n2.Category Description\n3.Exit\nEnter the number of the field you want to update:");
        Validation validationService = new Validation();
        try {
            switch (sc.nextInt()) {
                case 1 -> category.setName(validationService.name("📝 Enter the new Category Name:"));
                case 2 -> category.setDescription(validationService.address("📝 Enter the new Category Description:"));
                case 3 -> {
                    System.out.println("🚪 Exiting update operation.");
                    return;
                }
                default -> {
                    System.out.println("❌ Invalid choice. Please select 1 or 2.");
                    return;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
        }
        System.out.println("✅ Category updated successfully.");
    }


    public void delete() {
        Category category = getCategory(sc);
        if (category == null) {
            System.out.println("❌ No categories available to delete.");
            return;
        }

        System.out.println("🗑️ You have selected to delete the category: " + category.getName());
        System.out.println("This action can delete Your Product List Completely \n \t(yes/no) || (y/n)");
        String confirmation = sc.nextLine().trim().toLowerCase();
        if (confirmation.equals("yes") || confirmation.equals("y")) {
            if (categoryController.removeCategory(category))
                System.out.println("✅ Category deleted successfully.");
            else
                System.out.println("❌ Failed to delete category. Please try again.");
        } else {
            System.out.println("🚫 Category deletion cancelled.");
        }
    }

// help methods ;
//->  for update() and delete()  common logic  ( return the selected categories for update or delete)
//->  for add() product before   choosing category(return the selected category to Product helper)
//->  for viewAllTheCategoryProduct its return the category to viewCategoryForProducts()

    public  Category getCategory(Scanner sc) {
        if (categoryController.isCategoryEmpty()) {
            return null;
        }
        new CategoryView(loggedInUser).viewAllCategories();// display all categories
        int categoryIndex;
        do {
            System.out.println("🔢 Select a category by entering the corresponding number:");
            categoryIndex = sc.nextInt() - 1;
            sc.nextLine();
        } while (categoryIndex < 0 || categoryIndex >= categoryController.getCategories().size());

        return categoryController.getCategories().get(categoryIndex);
    }
}
