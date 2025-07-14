package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.model.*;
import src.com.zoho.ecommerce.util.OrderStatus;


public class OrderView  {
  private final User loggedInUser;

  private final int CUSTOMER = 1;
  private final int SELLER = 2;

  public OrderView(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }


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

    private  void printOrderStatus(String productName, int orderId, String transactionId, String status) {
        System.out.printf("""
            =================== 🧾 ORDER SUMMARY ===================
            📦 CustomerName    : %-30s
            🆔 Order ID        : %-30d
            🔁 Transaction ID  : %-30s
            🚚 Order Status    : %-30s
            ========================================================
            
            """, productName, orderId, transactionId, status);
    }

    public  void flow(Order order) {
        String[] statuses = new String[]{
                OrderStatus.CONFIRMED.getLabel(),
                OrderStatus.SHIPPED.getLabel(),
                OrderStatus.DELIVERED.getLabel()
        };

        for (String status : statuses) {
            printOrderStatus(order.getCustomer().getName(), order.getId(), order.getPayment(), status);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("⚠️ Status update interrupted.");
            }
        }
    }

}
