package src.com.zoho.ecommerce.util;

public enum OrderStatus {
    PENDING("⏳ Pending"),
    CONFIRMED("✅ Confirmed"),
    SHIPPED("🚚 Shipped"),
    DELIVERED("📬 Delivered");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}


