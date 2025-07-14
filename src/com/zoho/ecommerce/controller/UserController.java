package src.com.zoho.ecommerce.controller;

import java.util.List;
import src.com.zoho.ecommerce.model.Customer;
import src.com.zoho.ecommerce.model.Seller;
import src.com.zoho.ecommerce.model.User;

public class UserController {
    private static int idGenerator;
    private static final List<User> userList = DataManager.getDataManager().getUser();
    
// creation of Client(Customer)
    public  User createUser(String name, String phone, String email, String password, String gender, String address) {
        User user = new Customer(++idGenerator, name, phone, email, password, gender, address);
        userList.add(user);
        return user;
    }
// creation of seller
    public  User createUser(String name, String phone, String email, String password, String gender, String company, String companyAddress) {
        User user = new Seller(++idGenerator, name, phone, email, password, gender, company, companyAddress);
        userList.add(user);
        return user;
    }
    // checking duplicate mail exists
    public  boolean isMailExists(String email,User loggedInUser) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && ( loggedInUser != null && !user.getEmail().equals(loggedInUser.getEmail()))) {
                return true;
            }
        }
        return false;
    }
    // checking duplicate phone number exists
    public  boolean isPhoneExists(String phone,User loggedInUser) {

        for (User user : userList) {
            if (user.getPhone().equals(phone) && (loggedInUser != null && !user.getPhone().equals(loggedInUser.getPhone()))) {
                return true;
            }
        }
        return false;
    }
}
