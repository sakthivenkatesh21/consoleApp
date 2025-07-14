package src.com.zoho.ecommerce.model;

public class Product {

    private int id;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private Category category;
    private Seller seller;

    public Product() {
    }

    public Product(int id, String productName, String description, double price, 
                   int stock, Category category, Seller seller) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
        else {
            throw new IllegalArgumentException("Price must be greater than zero.");
        } 
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
        else {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }      
    }
// checking while product adding to the card
    public boolean isAvailableStock() {
        return stock <= 0;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        System.out.println("========================================");
        System.out.println("           Product Details              ");
        System.out.println("========================================");
        System.out.println("ID          : " + getId());
        System.out.println("Name        : " + getProductName());
        System.out.println("Price       : $" + getPrice());
        System.out.println("Quantity    : " + (getStock() == 0 ? "Out of Stock" : getStock()));
        System.out.println("Description : " + getDescription());
        System.out.println("Seller      : " + getSeller().getName());
        System.out.println("========================================");
        return "";
    }   
}
