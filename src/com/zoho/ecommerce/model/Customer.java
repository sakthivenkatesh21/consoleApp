package src.com.zoho.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final int CUSTOMER = 1;
    private Card card;
    private String address;
    private List<Order> previousOrderProduct;

    public Customer() {}

    public Customer(int id, String name,String phone, String email, String password, String gender, String address) {
        super(id, name, phone, email, password, gender);
        this.card = new Card();
        this.address = address;
        previousOrderProduct = new ArrayList<>();
        
    }
    public Card getcard() {
        return card;
    }

    public void setcard(Card card) {
        this.card = card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getPreviousOrderProduct() {
        return previousOrderProduct;
    }

    public void setPreviousOrderProduct(List<Order> previousOrderProduct) {
        this.previousOrderProduct = previousOrderProduct;
    }

    public int getRole(){
        return CUSTOMER;
    }
    
    @Override
    public String toString(){
        // System.out.println("Client ID : " + getId());
        System.out.println("Client Name : " + getName());
        System.out.println("Client Phone : " + getPhone());
        System.out.println("Client Email : " + getEmail());
        System.out.println("Address : " + address);
        
        return "";
    }
}
