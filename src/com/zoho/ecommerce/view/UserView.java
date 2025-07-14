package src.com.zoho.ecommerce.view;


import src.com.zoho.ecommerce.model.Customer;
import src.com.zoho.ecommerce.model.Seller;
import src.com.zoho.ecommerce.model.User;


public class UserView  {
    private final User loggedInUser ;

    private final int CUSTOMER =1;
    private final int SELLER   =2;

    public UserView(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    public void view() {
        String role = loggedInUser.getRole() == CUSTOMER ? "👤 Customer" : "🏢 Seller";
        System.out.println("""
                ╔═══════════════════════════════════════════════════╗
                |                📋 USER INFORMATION                |  
                ╚═══════════════════════════════════════════════════╝
                """);

        System.out.printf("🔑 Role              : %-30s%n", role);
        System.out.printf("🧑 Name              : %-30s%n", loggedInUser.getName());
        System.out.printf("📧 Email             : %-30s%n", loggedInUser.getEmail());
        System.out.printf("📱 Phone Number      : %-30s%n", loggedInUser.getPhone());
        System.out.printf("🏠 Address           : %-30s%n", (loggedInUser.getRole() == CUSTOMER ? ((Customer) loggedInUser).getAddress() : ((Seller) loggedInUser).getCompanyAddress()));
        // System.out.printf("🆔 User ID           : %-30s%n", loggedInUser.getId());

        if (loggedInUser.getRole() == SELLER) {
            Seller seller = (Seller) loggedInUser;
            System.out.printf("💰 Profit Earned     : ₹%-29.2f%n", seller.getProfit());
            System.out.printf("📦 Products Sold     : %-30d%n", seller.getSoldItem());
        }

        System.out.println("═════════════════════════════════════════════════════════");
    }
}
