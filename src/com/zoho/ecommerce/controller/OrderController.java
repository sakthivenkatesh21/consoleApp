package com.zoho.ecommerce.controller;

import com.zoho.ecommerce.model.*;
import com.zoho.ecommerce.model.Customer;

import java.util.List;

public class OrderController {
   
    private static int  idGenerator;
    private  final List<Order> orders = DataManager.getDataManager().getOrders();

    public  boolean isOrderEmpty() {
        return orders.isEmpty();
    }
// Order creation
    public   Order  createOrder(Card card, double amount,String payment, User loggedInUser) {
       
        Order order = new Order(++idGenerator,(Customer) loggedInUser,((Customer)loggedInUser).getAddress(),
                                    OrderStatus.CONFIRMED,amount,payment);
        updateSellerSales(card.getProduct());
        productOrder(order.getProduct(), card.getProduct());
        card.getProduct().clear();
        orders.add(order);
        
        ((Customer)loggedInUser).getPreviousOrderProduct().add(order);
        return order;
        
    }
    // updating seller's sales and profit and adding product to seller's saled list
    private  void updateSellerSales(List<CardProduct> products) {
        for(CardProduct product : products) {
            product.setProducStatus(OrderStatus.CONFIRMED);
            product.getProduct().getSeller().setSoldItem(product.getProduct().getSeller().getSoldItem() + product.getQuantity());
            product.getProduct().getSeller().setProfit(product.getProduct().getSeller().getProfit() +(product.getProduct().getPrice()*product.getQuantity()));
            product.getProduct().getSeller().getSaledList().add(product);
        }
    }
    // adding bought product to the Order list
    private   List<CardProduct>  productOrder(List<CardProduct> orderProducts, List<CardProduct> cardProducts) {
        for (CardProduct product : cardProducts)
                orderProducts.add(product);
        return orderProducts;
    }

}
