package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.interfaceController.Creatable;
import src.com.zoho.ecommerce.interfaceController.Deletable;
import src.com.zoho.ecommerce.interfaceController.Editable;
import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.interfaceController.Viewable;
import src.com.zoho.ecommerce.model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WishlistHandler implements Execute, Creatable, Editable, Viewable, Deletable {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;
    private Product product;
    private Card card;
    private  ProductController productController = new ProductController();
    public WishlistHandler(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public WishlistHandler(Product product, User loggedInUser) {
        this.product = product;
        this.loggedInUser = loggedInUser;
    }



    @Override
    public void operation() {
        while (true) {
            System.out.println("========================================");
            System.out.println("         🌟 Wish List Management 🌟         ");
            System.out.println("========================================");
            System.out.println("1️⃣ Add to Wish List");
            System.out.println("2️⃣ View Wish List");
            System.out.println("3️⃣ Update Wish List");
            System.out.println("4️⃣ Delete from Wish List");
            System.out.println("0️⃣ Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> new ProductService( loggedInUser).view();
                    case 2 -> view();
                    case 3 -> update();
                    case 4 -> delete();
                    case 0 -> {
                        System.out.println("🚪 Exiting Wish List Management.");
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
    public void add() {
        card = ((Customer) loggedInUser).getcard();
        System.out.println("🛒 Enter a Quantity for the product: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        if (productController.isProductExistCard(card.getProduct(), product.getId())) {
            System.out.println("⚠️ Product already exists in the cart. Please update the quantity instead.");
            return;
        }
        if(canAddToCard(product, quantity)){
            CardProduct cardProduct = new CardProduct(product.getId(), quantity);
            if (quantity > 10) {
                System.out.println("⚠️ You cannot add more than 10 items to the cart.");
                quantity = 10;
                cardProduct.setQuantity(quantity);
            }
            card.getProduct().add(cardProduct);
            System.out.println("✅ Product added to the cart successfully: " + product.getProductName());
            System.out.println("📦 Updated Quantity: " + cardProduct.getQuantity());
        }
        else {
            System.out.println("❌ Cannot add product to the cart. Please check the stock availability.");
        }
    }

    @Override
    public void view() {
        if (loggedInUser.getRole() == 1) {
            card = ((Customer) loggedInUser).getcard();
            if (card.getProduct().isEmpty()) {
                System.out.println("📭 Your WishList is empty.");
                return;
            }
            for (int i = 0; i < card.getProduct().size(); i++) {

                System.out.println("🛍️ Product " + (i + 1) + ": " + productController.getIsProductExist(card.getProduct().get(i).getProductId()).getProductName() + " 💵 Price: " + (card.getProduct().get(i).getQuantity() * productController.getIsProductExist(card.getProduct().get(i).getProductId()).getPrice()));
                System.out.println("\t 📦 Quantity: " + card.getProduct().get(i).getQuantity() + " 🛒 OrderStatus: " + card.getProduct().get(i).getProducStatus());
                System.out.println("-----------------------------");
            }
        }
    }

    @Override
    public void update() {
        card = ((Customer) loggedInUser).getcard();
        if (card.getProduct().isEmpty()) {
            System.out.println("📭 Your Wish List is empty. Please add products to the Wish List first.");
            return;
        }
        CardProduct cardProduct = checkCardProduct();
        if (cardProduct == null) return;
        System.out.println("✏️ Enter the new quantity for the product:");
        int oldQuantity = cardProduct.getQuantity();
        int newQuantity = sc.nextInt();
        sc.nextLine();
        if(canAddToCard(productController.getIsProductExist(cardProduct.getProductId()),newQuantity)){
             cardProduct.setQuantity(newQuantity);
             if (newQuantity > 10) {
                System.out.println("⚠️ You cannot add more than 10 items to the cart.");
                newQuantity = 10;
                cardProduct.setQuantity(newQuantity);
            }
            System.out.println("✅ Product Quantity updated successfully: " + productController.getIsProductExist(cardProduct.getProductId()).getProductName() + " 📦 Updated Quantity: " + newQuantity);

        }else {
            cardProduct.setQuantity(oldQuantity);
            System.out.println("❌ Cannot update product quantity. Please check the stock availability.");
        }
        
    }

    @Override
    public void delete() {
        card = ((Customer) loggedInUser).getcard();
        if (card.getProduct().isEmpty()) {
            System.out.println("📭 Your Wish List is empty. Please add products to the Wish List first.");
            return;
        }
        while (true) {
            CardProduct cardProduct = checkCardProduct();
            if (cardProduct == null) return;
            card.getProduct().remove(cardProduct);
            System.out.println("🗑️ Product " + productController.getIsProductExist(cardProduct.getProductId()).getProductName()+ " has been removed from your cart.");
        }
    }
    
// checking the card product quantity is availble or out of stock
    public void checkQuantityExist(List<CardProduct> cardProduct) {
        for (CardProduct obj : cardProduct) {
            Product products =   productController.getIsProductExist(obj.getProductId());
            if (products != null) {
                if (products.getStock() < obj.getQuantity()) {
                    if (products.getStock() > 0) {
                        System.out.println("⚠️ " + products.getProductName() + " Stock Available: " + products.getStock() + " Your Chosen Quantity: " + obj.getQuantity());
                        obj.setQuantity(products.getStock());
                        System.out.println("✅ Available Stock " + products.getStock() + " Updated to " + products.getProductName());
                    } else {
                        System.out.println("❌ The Product " + products.getProductName() + " is Out Of Stock.");
                        cardProduct.remove(obj);
                        System.out.println("🗑️ It has been removed from the cart.");
                        return;
                    }
                } else {
                    System.out.println("Product Choosed :" + products.getProductName() + " Quantity: " + obj.getQuantity() + " Price " + products.getPrice());
                }
            } else {
                System.out.println("❌ The Product   is not available.");
                cardProduct.remove(obj);
                System.out.println("🗑️ It has been removed from the cart.");
                
            }
        }
    }
// helper methods for update and remove common logics
    private CardProduct checkCardProduct() {
        view();
        System.out.println("🔢 Enter the Product ID from the cart \n Or Enter '-1' to Exit: ");
        int indexId = sc.nextInt();
        if (indexId == -1) {
            System.out.println("🚪 You have chosen to proceed with Exit.");
            return null;
        } else if (indexId > card.getProduct().size() || indexId <= 0) {
            System.out.println("❌ Invalid Card ID.");
            return null;
        }
        return card.getProduct().get(indexId - 1);
    }

    private boolean canAddToCard(Product product, int quantity){
        return product.getStock() >= quantity && quantity >0;
    }

    public double calculateCardTotal(Card obj) {
        double total = 0;
        for (CardProduct prod : obj.getProduct()) {
            total += prod.getQuantity() * productController.getIsProductExist(prod.getProductId()).getPrice();
        }
        return total;
    }
}
