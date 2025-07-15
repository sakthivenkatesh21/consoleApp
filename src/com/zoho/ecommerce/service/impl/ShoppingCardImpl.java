package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.exception.CartOperationException;
import src.com.zoho.ecommerce.exception.ProductNotFoundException;
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
        try {
            card = ((Customer) loggedInUser).getcard();
            System.out.println("🛒 Enter a Quantity for the product: ");
            int quantity = sc.nextInt();
            sc.nextLine();
            if (productController.isProductExistCard(card.getProduct(), product.getId())) {
                throw new CartOperationException("⚠️ Product already exists in the cart. Please update the quantity instead.");
            }
            if (canAddToCard(product, quantity)) {
                CardProduct cardProduct = new CardProduct(product.getId(), quantity);
                if (quantity > 10) {
                    System.out.println("⚠️ You cannot add more than 10 items to the cart.");
                    quantity = 10;
                    cardProduct.setQuantity(quantity);
                }
                card.getProduct().add(cardProduct);
                System.out.println("✅ Product added to the cart successfully: " + product.getProductName());
                System.out.println("📦 Updated Quantity: " + cardProduct.getQuantity());
            } else {
                throw new CartOperationException("❌ Cannot add product to the cart. Please check the stock availability.");
            }
        } catch (CartOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        try {
            card = ((Customer) loggedInUser).getcard();
            if (card.getProduct().isEmpty()) {
                throw new CartOperationException("📭 Your Wish List is empty. Please add products to the Wish List first.");
            }
            CardProduct cardProduct = checkCardProduct();
            if (cardProduct == null) return;
            System.out.println("✏️ Enter the new quantity for the product:");
            int oldQuantity = cardProduct.getQuantity();
            int newQuantity = sc.nextInt();
            sc.nextLine();
            Product prod = productController.getIsProductExist(cardProduct.getProductId());
            if (prod == null) {
                throw new ProductNotFoundException("❌ Product not found.");
            }
            if (canAddToCard(prod, newQuantity)) {
                cardProduct.setQuantity(newQuantity);
                if (newQuantity > 10) {
                    System.out.println("⚠️ You cannot add more than 10 items to the cart.");
                    newQuantity = 10;
                    cardProduct.setQuantity(newQuantity);
                }
                System.out.println("✅ Product Quantity updated successfully: " + prod.getProductName() + " 📦 Updated Quantity: " + newQuantity);
            } else {
                cardProduct.setQuantity(oldQuantity);
                throw new CartOperationException("❌ Cannot update product quantity. Please check the stock availability.");
            }
        } catch (CartOperationException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete() {
        try {
            card = ((Customer) loggedInUser).getcard();
            if (card.getProduct().isEmpty()) {
                throw new CartOperationException("📭 Your Wish List is empty. Please add products to the Wish List first.");
            }
            while (true) {
                CardProduct cardProduct = checkCardProduct();
                if (cardProduct == null) return;
                card.getProduct().remove(cardProduct);
                Product prod = productController.getIsProductExist(cardProduct.getProductId());
                String prodName = (prod != null) ? prod.getProductName() : "Unknown";
                System.out.println("🗑️ Product " + prodName + " has been removed from your cart.");
            }
        } catch (CartOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    // checking the card product quantity is available or out of stock
    public void checkQuantityExist(List<CardProduct> cardProduct) {
        for (int i = 0; i < cardProduct.size(); i++) {
            CardProduct obj = cardProduct.get(i);
            Product products = productController.getIsProductExist(obj.getProductId());
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

    private boolean canAddToCard(Product product, int quantity) {
        return product.getStock() >= quantity && quantity > 0;
    }

    public double calculateCardTotal(Card obj) {
        double total = 0;
        for (CardProduct prod : obj.getProduct()) {
            total += prod.getQuantity() * productController.getIsProductExist(prod.getProductId()).getPrice();
        }
        return total;
    }
}
