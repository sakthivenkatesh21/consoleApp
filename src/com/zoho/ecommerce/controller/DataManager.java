package src.com.zoho.ecommerce.controller;

import src.com.zoho.ecommerce.model.Category;
import src.com.zoho.ecommerce.model.Order;
import src.com.zoho.ecommerce.model.Product;
import src.com.zoho.ecommerce.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private DataManager() {
        user = new ArrayList<>();
        orders = new ArrayList<>();
        product = new HashMap<>();
        category = new ArrayList<>();
    }

    private final List<User> user;
    private final List<Order> orders;
    private final Map<Integer,Product> product;
    private final List<Category> category;
    private static DataManager dataManager;

    public static DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public List<User> getUser() {
        return user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Map<Integer,Product> getProduct() {
        return product;
    }

    public List<Category> getCategory() {
        return category;
    }
}
