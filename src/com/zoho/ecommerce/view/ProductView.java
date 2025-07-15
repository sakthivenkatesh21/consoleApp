package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.exception.ProductOperationException;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.User;

import src.com.zoho.ecommerce.service.GlobalScanner;
import src.com.zoho.ecommerce.service.impl.ProductImpl;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductView   {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;

    private final ProductController productController = new ProductController();

    private final int  CUSTOMER = 1;
    private final int  SELLER = 2;
    
    public ProductView(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void view() throws ProductOperationException {
        ProductImpl productServiceImpl = new ProductImpl(loggedInUser);
        if (loggedInUser.getRole() == SELLER) {
            try {
                new CategoryView(loggedInUser).viewCategoryForProducts(sc);
            } catch (Exception e) {
                throw new ProductOperationException("Error while viewing categories for products.", e);
            }
        }
        else {
            while (true) {
                if (!clientView()) {
                    System.out.println("⚠️ No products available. Please add a product first.");
                    return;
                }

                System.out.println("------------------------------------------------");
                System.out.println("Choose an option:");
                System.out.println("------------------------------------------------");
                System.out.println("1. 🔎 Search Product");
                System.out.println("2. ❤️ Add to Wish List");
                System.out.println("0. 🔙 Back (Exit)");
                System.out.println("------------------------------------------------");
                System.out.print("Enter your choice: ");
                try {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1 -> productServiceImpl.search();
                        case 2 -> productServiceImpl.addingProductToCart();
                        case 0 -> {
                            System.out.println("🔙 Exiting to previous menu.");
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                    sc.nextLine();
                } catch (ProductOperationException e) {
                    throw e;
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }


    // display All products  
    private boolean clientView() throws ProductOperationException {
        Map<Integer,Product> product = productController.getProducts();
        if (product == null) return false;
        System.out.println("🛍️ Available Products:");
        System.out.println("------------------------------------------------");
        for (Product obj: product.values()) {
            System.out.println(obj);
        }
        return true;
    }

    // view the out-of-stock product
    public void viewReStock(List<Product> product) throws ProductOperationException {
        System.out.println("📦 Products available for restocking:");
        System.out.println("------------------------------------------------");
        for (Product obj : product) {
           System.out.println(" Product Id :"+obj.getId() +"Product Name :"+ obj.getProductName() +"  Stock :"+ obj.getStock());
        }
        System.out.println("------------------------------------------------");
    }   
    // private int viewMenu(){
    //     System.out.println("------------------------------------------------");
    //     System.out.println("Choose an option:");
    //     System.out.println("------------------------------------------------");
    //     System.out.println("1. 🔎 Search Product");
    //     System.out.println("2. ❤️ Add to Wish List");
    //     System.out.println("0. 🔙 Back (Exit)");
    //     System.out.println("------------------------------------------------");
    //     System.out.print("Enter your choice: ");
    //     try {
    //         int choice = sc.nextInt();
    //         sc.nextLine();
    //         switch (choice) {
    //             case 1 ->  search();
    //             case 2 ->  addingProductToCart();
    //             case 0 -> {
    //                 System.out.println("🔙 Exiting to previous menu.");
    //                 return 0;
    //             }
    //             default -> System.out.println("❌ Invalid choice. Please try again.");
    //         }
    //     } catch (InputMismatchException e) {
    //         System.out.println("❌ Invalid input. Please enter a number.");
    //         sc.nextLine();
    //     } catch (Exception e) {
    //         System.out.println("❌ An unexpected error occurred: " + e.getMessage());
    //     }
    // }

    // private void print(Map<Integer,Product> products){
    //     for(Product product : products.values()){
    //         System.out.println("\n========== Order Product ==========");
    //         System.out.println("Name                 : " + product.getProductName());
    //         System.out.println("Quantity Ordered     : " + quantity);
    //         System.out.println("Price                : $" + product.getPrice()*getQuantity());
    //         System.out.println("Date Time Added      : " + getFormattedDate() + " " + getFormattedTime());
    //         System.out.println("Order Status         : " + producStatus);
    //         System.out.println("==================================");
    //     }
   
    // }
}

