package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.model.*;
import src.com.zoho.ecommerce.service.GlobalScanner;
import src.com.zoho.ecommerce.view.CardView;

import java.util.List;
import java.util.Scanner;

public class ShoppingCardImpl {
    private final Scanner sc = GlobalScanner.getScanner();
    private final User loggedInUser;
    private Product product;
    private Card card;
    private final ProductController productController = new ProductController();

    public ShoppingCardImpl(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public ShoppingCardImpl(Product product, User loggedInUser) {
        this.product = product;
        this.loggedInUser = loggedInUser;
    }


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
        new CardView(loggedInUser).view();
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
