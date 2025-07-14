package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.controller.ProductController;

import src.com.zoho.ecommerce.model.*;

public class CardView {
    private final User loggedInUser;
    private final   ProductController productController = new ProductController();
    private  final int CUSTOMER =1;

    public CardView(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }



    public void view() {
        if (loggedInUser.getRole() == CUSTOMER) {
            Card card = ((Customer) loggedInUser).getcard();
            if (card.getProduct().isEmpty()) {
                System.out.println("📭 Your WishList is empty.");
                return;
            }
            for (int i = 0; i < card.getProduct().size(); i++) {

                System.out.println("🛍️ Product " + (i + 1) + ": " + productController.getIsProductExist(card.getProduct().get(i).getProductId()).getProductName() + " 💵 Price: " + (card.getProduct().get(i).getQuantity() * productController.getIsProductExist(card.getProduct().get(i).getProductId()).getPrice()));
                System.out.println("\t 📦 Quantity: " + card.getProduct().get(i).getQuantity() + " 🛒 OrderStatus: " + card.getProduct().get(i).getProducStatus());
                System.out.println("-----------------------------");
            }
        }
    }

}
