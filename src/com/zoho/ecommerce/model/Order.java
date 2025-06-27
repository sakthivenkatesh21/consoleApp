package com.zoho.ecommerce.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private String address;
    private LocalDateTime orderTime;
    private OrderStatus status;
    private double total;
    private String payment;
    private List<CardProduct> product;

    public Order() {
        this.product = new ArrayList<>();
    }

    public Order(int id, Customer customer, String address, OrderStatus status, double total,
                 String payment) {
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.orderTime = LocalDateTime.now();
        this.status = status;
        this.total = total;
        this.payment = payment;
        this.product = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
         this.customer  = customer;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<CardProduct> getProduct() {
        return product;
    }

    public void setProduct(List<CardProduct> product) {
        this.product = product;
    }

    public String getFormattedDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(orderTime);
    }

    public String getFormattedTime() {
        return DateTimeFormatter.ofPattern("hh:mm").format(orderTime);
    }

    public String  toString() {
        System.out.println("--------------------------------------------------");
        System.out.println("            🧾 Order Summary                      ");
        System.out.println("--------------------------------------------------");
        System.out.printf("📦 Order ID      : %s%n", id);
        System.out.printf("👤 Client        : %s%n",customer.getName());
        System.out.printf("📍 Address       : %s%n", address);
        System.out.printf("🕒 Order Time    : %s %s%n", getFormattedDate(), getFormattedTime());
        System.out.printf("📌 Status        : %s%n", status);
        System.out.printf("💰 Total Amount  : $%.2f%n", total);
        System.out.printf("💳 Payment Method: %s%n", payment);
        System.out.println("🛒 Products      ");
        System.out.println("--------------------------------------------------");
        for (CardProduct prod : product) {
            System.out.println("Product Name: " + prod.getProduct().getProductName());
            System.out.println("Quantity: " + prod.getQuantity());
            System.out.println("Price: $" +  prod.getProduct().getPrice()*prod.getQuantity());
            System.out.println("Seller Name: " +  prod.getProduct().getSeller().getName()+"  Company: " +  prod.getProduct().getSeller().getCompany());
            System.out.println("--------------------------------------------------");
        }
        System.out.println("--------------------------------------------------");
        return"";
    }
   }
