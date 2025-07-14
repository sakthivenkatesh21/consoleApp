package src.com.zoho.ecommerce.service.impl;

import src.com.zoho.ecommerce.controller.OrderController;
import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.model.*;
import src.com.zoho.ecommerce.service.GlobalScanner;
import src.com.zoho.ecommerce.service.PaymentService;
import src.com.zoho.ecommerce.util.OrderStatus;
import src.com.zoho.ecommerce.view.OrderView;
import src.com.zoho.ecommerce.view.CardView;

import java.util.Scanner;

public class OrderServiceImpl   {
  private final Scanner sc =  GlobalScanner.getScanner();
  private final User loggedInUser;
  private final OrderController orderController = new OrderController();


  public OrderServiceImpl(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  public  void checkout() {
    if (((Customer) loggedInUser).getcard() == null || ((Customer) loggedInUser).getcard().getProduct() == null || ((Customer) loggedInUser).getcard().getProduct().isEmpty()) {
      System.out.println("⚠️ You must have a valid card to check out. Please add Product to a card and checkout your account.");
      return;
    }

      Card card = ((Customer)loggedInUser).getcard();
      ShoppingCardImpl wishlistHandler = new ShoppingCardImpl( loggedInUser);
      new CardView(loggedInUser).view();
      System.out.println("👉 Enter 'Yes | Y' to confirm checkout\n\t OR\n'No | N' Remove Product From Card:");
      String choice = sc.nextLine().trim().toUpperCase();

        switch (choice) {
            case "NO":
            case "N" :
                 wishlistHandler.delete();
            case "YES":
            case  "Y" :
                System.out.println("✅ Proceeding to checkout...");
                wishlistHandler.checkQuantityExist(card.getProduct());
                if( card.getProduct().isEmpty()) {
                    System.out.println("⚠️ No products in the card to checkout.");
                    return;
                }
                double cardTotal = wishlistHandler.calculateCardTotal(card);
                String payment = new PaymentService().paymentProcess( cardTotal);
                if (payment == null) {
                    System.out.println("❌ Payment failed. Please try again.");
                    return;
                }
                Order order = orderController.createOrder(card, cardTotal, payment, loggedInUser);
                if (order != null &&  new ProductController().reduceStock(order.getProduct())) {
                    new OrderView(loggedInUser).flow(order);
                    order.setStatus(OrderStatus.DELIVERED);
                   
                } else {
                    System.out.println("❌ Order creation failed. Please try again.");
                    System.out.println("Amount will be refunded to your account.");
                }
              break;  
            default : System.out.println("❌ Invalid choice. Please enter 'Yes' or 'No'.");
        }
    
  }
}
