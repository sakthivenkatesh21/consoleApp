package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.model.Category;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.User;
import src.com.zoho.ecommerce.service.GlobalScanner;
import src.com.zoho.ecommerce.util.Validation;
import src.com.zoho.ecommerce.view.CategoryView;
import src.com.zoho.ecommerce.view.ProductView;

import java.util.InputMismatchException;
import java.util.List;

import java.util.Scanner;

public class ProductImpl {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;

    private final ProductController productController = new ProductController();

    public ProductImpl(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void add() {
        Validation validator = new Validation();

        Category category = new ProductCategoryImpl(loggedInUser).getCategory(sc);
        if (category == null  ) {
            System.out.println("⚠️ No category available. Please add a category first.");
            return;
        }
        String productName = validator.name("📝 Enter a New Product Name:");
        String productDescription = validator.address("📝 Enter a New Product Description:");
        double productPrice = getPrice("💰 Enter the new Product Price:");
        int productStock = getStock("📦 Enter the new Product Stock:");
        Product product = productController.createProduct(productName,productDescription,productPrice,productStock,category,loggedInUser);
        if (product == null) {
            System.out.println("❌ Product with the same name already exists.");
        } else {
            System.out.println("✅ Product added successfully: " + product.getProductName());
        }
    }

    public void update() {
        Validation validator = new Validation();

        Product product = checkGetProduct();
        if (product != null) {
            System.out.println("📝 Current Product Details: " + product);
            System.out.println("1.Product Name \n2.Product Description \n3.Product Price \n4.Product Stock");

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        product.setProductName(validator.name("📝 Enter New Product Name:"));
                        System.out.println("✅ Product name updated successfully: " + product.getProductName());
                    }
                    case 2 -> {
                        product.setDescription(validator.address("📝 Enter New Product Description:"));
                        System.out.println("✅ Product description updated successfully: " + product.getDescription());
                    }
                    case 3 -> {
                        product.setPrice(getPrice("💰 Enter New Product Price:"));
                        System.out.println("✅ Product price updated successfully: " + product.getPrice());
                    }
                    case 4 -> {
                        product.setStock(getStock("📦 Enter New Product Stock:"));
                        System.out.println("✅ Product stock updated successfully: " + product.getStock());
                    }
                    default -> {
                        System.out.println("❌ Invalid choice. Please try again.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        } else {
                System.out.println("❌ Product not found or could not be updated.");
        }
    }

    
    public void delete() {

        Product product = checkGetProduct();
        if (product != null) {
            if (productController.removeProduct(product)) {
                System.out.println("✅ Product removed successfully: " + product.getProductName());
            } else {
                System.out.println("❌ Product not found or could not be removed.");
            }
        }
    }


// Common logic For Update and Delete

    private Product checkGetProduct() {
        // (viewCategoryForProducts)Methods Call to Display All Category with Products 
        Product product  = null;
        if(new CategoryView(loggedInUser).viewCategoryForProducts(sc)){
            System.out.println("🆔 Enter the Product ID  or 0 to exit:");
            try {
                int productId = sc.nextInt();
                sc.nextLine();
                if (productId == 0) 
                    System.out.println("🔙 Exiting to previous menu.");
                else
                 product = productController.getIsProductExist(productId);
            }
            catch( InputMismatchException e){
                System.out.println("❌ Invalid input. Please enter a valid Product ID.");
                sc.nextLine();
            }
            catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
        return product;
    }

    public void search() {
        System.out.println("🔎 Enter a Product Name to search:");
        String productName = sc.nextLine();
        List<Product> product = productController.isProductExists(productName);
        if (product == null) {
            System.out.println("❌ Product not found. Please try again.");
            return;
        }
        for (Product obj : product) {
            System.out.println("✅ Product found in Category: " + obj.getCategory().getName());
            System.out.println("📝 Product Details: \n  " + obj);
        }
        // if(viewMenu()==0){
            
        
        System.out.println(" *        🔎 Product Search Options    *" );
        System.out.println("****************************************");
        System.out.println("1. ❤️ Add to Wish List");
        System.out.println("2. 🔎 Search Product Again");
        System.out.println("3. 🔙 Back (Exit)");
        System.out.println("****************************************");
        System.out.print("Enter your choice: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    if (!addingProductToCart())
                        System.out.println("🔙 Exiting to previous menu.");
                }
                case 2 -> search();
                default -> System.out.println("🔙 Exiting to previous menu.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
    }

//  Adding Product to Wish List(View And Add Product to Wish List)
    // private void addWishList() {
      
    //     while (true) {
    //         if(clientView()){
    //             if (!addingProductToCart()) {
    //                 System.out.println("🔙 Exiting to previous menu.");
    //                 return;
    //             }
    //         }      
    //     }
    // }
    
// logic of adding Product to Wish List (Add Product to Wish List)

    public boolean addingProductToCart() {
        System.out.println("🆔 Enter the Product ID to add to cart or 0 to exit:");
        int productId = sc.nextInt();
        sc.nextLine();
        if (productId == 0) return false;
        Product product = productController.getIsProductExist(productId);
        if (product == null )  {
            System.out.println("❌ Product not found in the selected category.");
            return false;
        }
        else if(product.isAvailableStock()) {
            System.out.println("⚠️ Product is out of stock.");
            return false;
        }
        System.out.println("✅ Product found: \n" + product);
        ShoppingCardImpl addCard = new ShoppingCardImpl(product, loggedInUser);
        addCard.add();
        return true;
    }

    // input validation for product price (Reuse Methods for update and add)
    private double  getPrice(String info){
        System.out.println(info);
        double productPrice = sc.nextDouble();
        while (productPrice < 0) {
            System.out.println("❌ Invalid price! Please enter a valid price greater than 0.");
            productPrice = sc.nextDouble();
        }
        sc.nextLine();
        return productPrice;
    }
// input validation for product Stock (Reuse Methods for update and add)
    private  int getStock(String info){
        
        System.out.println(info);
        int productStock = sc.nextInt();
        while (productStock <= 0 || productStock > 100) {
            System.out.println("❌ Invalid stock! Please enter a valid stock between 0 and 100.");
            productStock = sc.nextInt();
        }
        sc.nextLine();
        return productStock;
    }

// restock the products in the list
    public void  reStockAll(List<Product> product) {
        while(true){
            new ProductView(loggedInUser).viewReStock(product);
            System.out.println("Enter a Product Id to update the stock or 0 to exit:");
            int productId = sc.nextInt();
            sc.nextLine();
            if (productId == 0) {
                System.out.println("🔙 Exiting to previous menu.");
                return;
            }
            Product selectedProduct = productController.getIsProductExist(productId);
            if (selectedProduct == null || !product.contains(selectedProduct)) {
                System.out.println("❌ Product not found in the selected category.");
                return;
            }
            selectedProduct.setStock(getStock("📦 Update  the  Product Stock for " + selectedProduct.getProductName() + ":"));
            System.out.println("✅ Product " + selectedProduct.getProductName() + " stock updated successfully to: " + selectedProduct.getStock());   
        }
       
    }

}

