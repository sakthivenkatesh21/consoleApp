package com.zoho.ecommerce.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardProduct   {

    private Product product;
    private int quantity;
    private LocalDateTime prodTimeDateAdded;
    private OrderStatus producStatus;

    public CardProduct() {}

    public CardProduct(Product product, int quantity) {
//        super(product.getId(), product.getProductName(), product.getDescription(), product.getPrice(),
//              product.getStock(), product.getCategory(), product.getSeller());
        this.product = product;
        this.quantity = quantity;
        this.prodTimeDateAdded = LocalDateTime.now();
        this.producStatus = OrderStatus.PENDING;
    }

    public CardProduct(int quantity, LocalDateTime prodTimeDateAdded, OrderStatus producStatus) {
        this.quantity = quantity;
        this.prodTimeDateAdded = prodTimeDateAdded;
        this.producStatus = producStatus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getprodTimeDateAdded() {
        return prodTimeDateAdded;
    }

    public void setprodTimeDateAdded(LocalDateTime prodTimeDateAdded) {
        this.prodTimeDateAdded = prodTimeDateAdded;
    }

    public String getFormattedDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(prodTimeDateAdded);
    }

    public String getFormattedTime() {
        return DateTimeFormatter.ofPattern("hh:mm").format(prodTimeDateAdded);
    }

    public OrderStatus getProducStatus() {
        return producStatus;
    }

    public void setProducStatus(OrderStatus producStatus) {
        this.producStatus = producStatus;
    }

// check card quantity exits product stock
    public boolean canAddToCard() {
        
        return product.getStock() >= getQuantity();
    }

    @Override
    public String toString() {
        System.out.println("\n========== Order Product ==========");
        System.out.println("Name                 : " + product.getProductName());
        System.out.println("Quantity Ordered     : " + quantity);
        System.out.println("Price                : $" + product.getPrice()*getQuantity());
        System.out.println("Date Time Added      : " + getFormattedDate() + " " + getFormattedTime());
        System.out.println("Order Status         : " + producStatus);
        System.out.println("==================================");
        return "";
    }

    // public boolean reStock() {
    //     if(canAddToCard()){
    //         //setStock( getStock()-getQuantity());
    //         return true;
    //     }
    //     return false;
    // }
}
