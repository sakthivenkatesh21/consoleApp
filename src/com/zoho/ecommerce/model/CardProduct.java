package com.zoho.ecommerce.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardProduct   {

    private int productId;
    private int quantity;
    private LocalDateTime prodTimeDateAdded;
    private OrderStatus producStatus;

    public CardProduct() {}

    public CardProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.prodTimeDateAdded = LocalDateTime.now();
        this.producStatus = OrderStatus.PENDING;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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



    // @Override
    // public String toString() {
    //     System.out.println("\n========== Order Product ==========");
    //     System.out.println("Name                 : " + product.getProductName());
    //     System.out.println("Quantity Ordered     : " + quantity);
    //     System.out.println("Price                : $" + product.getPrice()*getQuantity());
    //     System.out.println("Date Time Added      : " + getFormattedDate() + " " + getFormattedTime());
    //     System.out.println("Order Status         : " + producStatus);
    //     System.out.println("==================================");
    //     return "";
    // }

    // public boolean reStock() {
    //     if(canAddToCard()){
    //         //setStock( getStock()-getQuantity());
    //         return true;
    //     }
    //     return false;
    // }
}
