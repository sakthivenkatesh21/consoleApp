package src.com.zoho.ecommerce.controller;

import src.com.zoho.ecommerce.model.User;
import java.util.List;

public class SignIn {

    private static final List<User> user = DataManager.getDataManager().getUser();
    
// checking login
    public static User validateLogIn(String email, String password) {
        for (User data : user) {
            if (data.getEmail().equals(email) && data.getPassword().equals(password)) {
                return data;
            }
        }
        return null;
    }
}
