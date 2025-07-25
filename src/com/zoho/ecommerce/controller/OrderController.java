package src.com.zoho.ecommerce.controller;

import src.com.zoho.ecommerce.model.Card;
import src.com.zoho.ecommerce.model.CardProduct;
import src.com.zoho.ecommerce.model.Customer;
import src.com.zoho.ecommerce.model.Order;
import src.com.zoho.ecommerce.util.OrderStatus;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.User;

import java.util.List;


public class OrderController {
   
    private static int  idGenerator;
    private  final List<Order> orders = DataManager.getDataManager().getOrders();
    private final ProductController productController = new ProductController();

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
        for(CardProduct cardProduct : products) {
            Product product = productController.getIsProductExist(cardProduct.getProductId());
            cardProduct.setProducStatus(OrderStatus.CONFIRMED);
            product.getSeller().setSoldItem(product.getSeller().getSoldItem() + cardProduct.getQuantity());
            product.getSeller().setProfit(product.getSeller().getProfit() +(product.getPrice()*cardProduct.getQuantity()));
            product.getSeller().getSaledList().add(cardProduct);
        }
    }
    // adding bought product to the Order list
    private   void  productOrder(List<CardProduct> orderProducts, List<CardProduct> cardProducts) {
        orderProducts.addAll(cardProducts);
    }

}
