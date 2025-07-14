package src.com.zoho.ecommerce.service;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.impl.ProductImpl;
import src.com.zoho.ecommerce.view.ProductView;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductService implements Execute {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;

    private final ProductController productController = new ProductController();


    private final int  CUSTOMER = 1;
    private final int  SELLER = 2;
    
    public ProductService(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    @Override
    public void operation( ) {
        ProductImpl productServiceImpl = new ProductImpl(loggedInUser);
        ProductView productView = new ProductView(loggedInUser);
        while (true) {
            System.out.println("=========================================");
            System.out.println("          🛍️ Product Management Menu        ");
            System.out.println("=========================================");
            if (loggedInUser.getRole() == CUSTOMER) {
                System.out.println("1. 🔍 View Products");
                System.out.println("0. 🔙 Back (Exit)");
            }else if(loggedInUser.getRole() == SELLER) {
                System.out.println("1. ➕ Add Product");
                System.out.println("2. 🔍 View Product");
                System.out.println("3. ✏️ Update Product");
                System.out.println("4. ❌ Remove Product");
                if(!productController.getStockIsEmpty(loggedInUser))System.out.println("5. 📦 Re Stock");
                System.out.println("0. 🔙 Back (Exit)");
            }
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        if (loggedInUser.getRole() == CUSTOMER)
                            productView.view();
                        else
                            productServiceImpl.add();
                    }
                    case 2 -> productView.view();
                    case 3 -> {if(loggedInUser.getRole() ==SELLER)productServiceImpl.update();}
                    case 4 -> {if(loggedInUser.getRole() ==SELLER)productServiceImpl.delete();}
                    case 5 -> {if(loggedInUser.getRole() == SELLER)reStock(productServiceImpl,productView);}
                    case 0 -> {
                        System.out.println("🔙 Exiting to previous menu.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // restock the Seller product
    private void reStock(ProductImpl productServiceImpl, ProductView productView) {
        List<Product> product = productController.getEmptyStockProducts(loggedInUser);
        while(true){
            System.out.println("1. 📦 Restock  Products\n2.View ReStock \n0.Exit");
            System.out.print("👉 Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1->  productServiceImpl.reStockAll(product);
                    case 2->  productView.viewReStock(product);
                    case 0->  {
                        System.out.println("🔙 Exiting to previous menu.");
                        return;
                    }
                    default-> {
                        System.out.println("❌ Invalid choice. Please try again.");
                        return;
                    }
                }
            }catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }

}

