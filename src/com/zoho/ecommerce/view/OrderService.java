package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.OrderController;
import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.interfaceController.Execute;
import src.com.zoho.ecommerce.interfaceController.Viewable;
import src.com.zoho.ecommerce.model.Card;
import src.com.zoho.ecommerce.model.CardProduct;
import src.com.zoho.ecommerce.model.Customer;
import src.com.zoho.ecommerce.model.Order;
import src.com.zoho.ecommerce.model.OrderStatus;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.Seller;
import src.com.zoho.ecommerce.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderService implements Execute, Viewable {
  private final Scanner sc =  GlobalScanner.getScanner();
  private final User loggedInUser;
  private final OrderController orderController = new OrderController();

  private final int CUSTOMER = 1;
  private final int SELLER = 2;

  public OrderService(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }


  @Override
  public void operation() {
    while (true) {
      try {
        if (loggedInUser.getRole() == SELLER) {
          System.out.println("\n==============================");
          System.out.println("📦 1. View Orders");
          System.out.println("🚪 0. Exit");
          System.out.println("==============================");
        } else if (loggedInUser.getRole() == CUSTOMER) {
          System.out.println("\n==============================");
          System.out.println("🛒 1. Checkout");
          System.out.println("📦 2. View Order");
          System.out.println("🚪 0. Exit");
          System.out.println("==============================");
        }
        System.out.print("👉 Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
          case 1 -> {
            if (loggedInUser.getRole() == SELLER) view();
            if(loggedInUser.getRole()  == CUSTOMER) checkout();
          }
          case 2 -> {
            if (loggedInUser.getRole() == CUSTOMER) view();
            else System.out.println("❌ Invalid choice. Please try again.");
          }
          case 0 -> {
            System.out.println("🚪 Exiting Order Management.");
            return;
          }
          default -> System.out.println("❌ Invalid choice. Please try again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("❌ Invalid input. Please enter a valid number.");
        sc.nextLine();
      } catch (Exception e) {
        System.out.println("❌ An unexpected error occurred: " + e.getMessage());
      }
    }
  }

  private void checkout() {
    if (((Customer) loggedInUser).getcard() == null || ((Customer) loggedInUser).getcard().getProduct() == null || ((Customer) loggedInUser).getcard().getProduct().isEmpty()) {
      System.out.println("⚠️ You must have a valid card to check out. Please add Product to a card and checkout your account.");
      return;
    }

      Card card = ((Customer)loggedInUser).getcard();
      WishlistHandler wishlistHandler = new WishlistHandler( loggedInUser);
      wishlistHandler.view();
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
                    OrderStatusUpdate.flow(order);
                    order.setStatus(OrderStatus.DELIVERED);
                   
                } else {
                    System.out.println("❌ Order creation failed. Please try again.");
                    System.out.println("Amount will be refunded to your account.");
                }
              break;  
            default : System.out.println("❌ Invalid choice. Please enter 'Yes' or 'No'.");
        }
    
  }

  @Override
  public void view() {
      switch (loggedInUser.getRole()) {
          case CUSTOMER -> dispalyCustomerOrders( loggedInUser);
          case SELLER -> displaySellerOrders( loggedInUser);
          default -> System.out.println("⛔ You are not authorized to view orders.");
      }
  }


  // view orders for customer
  private void dispalyCustomerOrders( User loggedInUser) {
    System.out.println("📋 Displaying orders for customer: " + loggedInUser.getName());
    if (((Customer) loggedInUser).getPreviousOrderProduct() == null || ((Customer) loggedInUser).getPreviousOrderProduct().isEmpty()) {
      System.out.println("⚠️ No previous orders found for this customer.");
      return;
    }
    for (int i = 0; i < ((Customer) loggedInUser).getPreviousOrderProduct().size(); i++) {
      System.out.println("📦 Order " + (i + 1) );
      printOrder(((Customer) loggedInUser).getPreviousOrderProduct().get(i));
    }
  }


  // view orders for seller
  private void displaySellerOrders( User loggedInUser) {
    System.out.println("📋 Displaying placed orders for seller: " + loggedInUser.getName());
    if (((Seller) loggedInUser).getSaledList() == null || ((Seller) loggedInUser).getSaledList().isEmpty()) {
      System.out.println("⚠️ No orders found for this seller.");
      return;
    }
    for (int i = 0; i < ((Seller) loggedInUser).getSaledList().size(); i++) {
      System.out.println("📦 Order Sold " + (i + 1) );
      System.out.println(((Seller) loggedInUser).getSaledList().get(i));
    }
  }

  private void printOrder(Order order){

        System.out.println("--------------------------------------------------");
        System.out.println("            🧾 Order Summary                      ");
        System.out.println("--------------------------------------------------");
        System.out.printf("📦 Order ID      : %s%n", order.getId());
        System.out.printf("👤 Client        : %s%n",order.getCustomer().getName());
        System.out.printf("📍 Address       : %s%n", order.getAddress());
        System.out.printf("🕒 Order Time    : %s %s%n", order.getFormattedDate(), order.getFormattedTime());
        System.out.printf("📌 Status        : %s%n", order.getStatus());
        System.out.printf("💰 Total Amount  : $%.2f%n", order.getTotal());
        System.out.printf("💳 Payment Method: %s%n", order.getPayment());
        System.out.println("🛒 Products      ");
        System.out.println("--------------------------------------------------");

        ProductController productController = new ProductController();
        for (CardProduct prod : order.getProduct()) {
            Product obj = productController.getIsProductExist(prod.getProductId());
            System.out.println("Product Name: " +obj.getProductName());
            System.out.println("Quantity: " + prod.getQuantity());
            System.out.println("Price: $" +  obj.getPrice()*prod.getQuantity());
            System.out.println("Seller Name: " + obj.getSeller().getName()+"  Company: " +  obj.getSeller().getCompany());
            System.out.println("--------------------------------------------------");
        }
        System.out.println("--------------------------------------------------");
  }

}
