package src.com.zoho.ecommerce.service;


import src.com.zoho.ecommerce.controller.CategoryController;
import src.com.zoho.ecommerce.controller.DataManager;
import src.com.zoho.ecommerce.controller.ProductController;
import src.com.zoho.ecommerce.controller.UserController;
import src.com.zoho.ecommerce.model.Category;
import src.com.zoho.ecommerce.model.User;

import java.util.List;

final class DefaultProductEntry {

    // use private final to prevent modification of data
    private final  List<User> userList = DataManager.getDataManager().getUser();
    private final  List<Category> categoryList = DataManager.getDataManager().getCategory();

    //default constructor to create product data
    DefaultProductEntry() {
        createCategoryData();
        createSeller();
        createProductData();
    }

    // creating product data
    void  createProductData() {
        ProductController productController = new ProductController();
        // Products by User 1
        productController.createProduct( "Poco X7 Smartphone", "Latest model smartphone", 800.00, 20, categoryList.get(0), userList.get(0));
        productController.createProduct( "Boat Headphones", "Noise-cancelling headphones", 200.00, 15, categoryList.get(0), userList.get(0));
        productController.createProduct( "Dell Inspiron Laptop", "15-inch laptop with SSD", 1000.00, 12, categoryList.get(0), userList.get(0));
        productController.createProduct( "Sony WH-1000XM4", "Premium noise-cancelling headphones", 350.00, 12, categoryList.get(0), userList.get(0));
        productController.createProduct( "JBL Flip 5", "Portable Bluetooth speaker", 100.00, 25, categoryList.get(0), userList.get(0));
        productController.createProduct( "Fossil Smartwatch", "Stylish smartwatch with fitness tracking", 250.00, 18, categoryList.get(2), userList.get(0));

        // Products by User 2
        productController.createProduct( "Apple Smartwatch", "Feature-rich smartwatch", 300.00, 25, categoryList.get(0), userList.get(1));
        productController.createProduct( "Samsung Galaxy Tab", "High-performance tablet", 600.00, 30, categoryList.get(0), userList.get(1));
        productController.createProduct( "HP Pavilion Laptop", "14-inch laptop with touchscreen", 1100.00, 10, categoryList.get(0), userList.get(1));
        productController.createProduct( "Kindle Paperwhite", "E-reader with adjustable light", 130.00, 22, categoryList.get(4), userList.get(1));
        productController.createProduct( "Fitbit Charge 4", "Fitness tracker with GPS", 150.00, 20, categoryList.get(2), userList.get(1));
        productController.createProduct( "Asus ROG Gaming Laptop", "High-performance gaming laptop", 2500.00, 6, categoryList.get(0), userList.get(1));

    // Products by User 3
        productController.createProduct("Nike Running Shoes", "Comfortable and durable running shoes", 120.00, 30, categoryList.get(6), userList.get(2));
        productController.createProduct("Philips Air Fryer", "Healthy cooking appliance", 150.00, 20, categoryList.get(3), userList.get(2));
        productController.createProduct("Ray-Ban Sunglasses", "Stylish sunglasses for outdoor use", 200.00, 15, categoryList.get(4), userList.get(2));
        productController.createProduct("Samsung Refrigerator", "Energy-efficient refrigerator", 800.00, 10, categoryList.get(3), userList.get(2));
        productController.createProduct("Loreal Shampoo", "Hair care product", 15.00, 50, categoryList.get(7), userList.get(2));
        productController.createProduct("Adidas Sports Jersey", "High-quality sports jersey", 50.00, 25, categoryList.get(6), userList.get(2));
        productController.createProduct("The Alchemist", "Inspirational book by Paulo Coelho", 10.00, 40, categoryList.get(5), userList.get(2));
        productController.createProduct("Sony PlayStation 5", "Next-gen gaming console", 500.00, 8, categoryList.get(0), userList.get(2));
        // Products by User 4

        productController.createProduct("Canon DSLR Camera", "High-quality DSLR camera", 1200.00, 10, categoryList.get(0), userList.get(3));
        productController.createProduct("Leather Wallet", "Premium leather wallet", 50.00, 20, categoryList.get(1), userList.get(3));
        productController.createProduct("Apple AirPods Pro", "Wireless noise-cancelling earbuds", 250.00, 15, categoryList.get(2), userList.get(3));
        productController.createProduct("Dyson Vacuum Cleaner", "Powerful and efficient vacuum cleaner", 400.00, 12, categoryList.get(3), userList.get(3));
        productController.createProduct("Gucci Handbag", "Luxury designer handbag", 1500.00, 5, categoryList.get(4), userList.get(3));
        productController.createProduct("Harry Potter Series", "Complete book series by J.K. Rowling", 100.00, 30, categoryList.get(5), userList.get(3));
        productController.createProduct("Wilson Tennis Racket", "Professional-grade tennis racket", 200.00, 10, categoryList.get(6), userList.get(3));
        productController.createProduct("Maybelline Lipstick", "Long-lasting lipstick", 20.00, 50, categoryList.get(7), userList.get(3));
    }

    // creating category data
    void createCategoryData() {
        CategoryController categoryController = new CategoryController();
        categoryController.createCategory( "Electronics", "Devices and gadgets");
        categoryController.createCategory( "Accessories", "Additional items for devices");
        categoryController.createCategory( "Wearables", "Smart wearable technology");
        categoryController.createCategory( "Home Appliances", "Appliances for home use");
        categoryController.createCategory( "Fashion", "Clothing and accessories");
        categoryController.createCategory( "Books", "Printed and digital books");
        categoryController.createCategory( "Sports", "Sports equipment and apparel");
        categoryController.createCategory( "Beauty", "Beauty and personal care products");
    }

    // creating seller data
    void createSeller() {
        UserController userController = new UserController();
        userController.createUser("Karthi","9025149404","karthi@gmail.com","Karthi@1","Male","Karthi Enterprises","Chennai");
        userController.createUser("Mani", "6869585858", "mani@gmail.com", "Mani@1", "Male", "Mani Industries", "Coimbatore");
        userController.createUser("Tanishka", "9585859685", "tanishka@gmail.com", "Tanishka@1", "Female", "Tanishka Creations", "Bangalore");
        userController.createUser("Muya", "8567890678", "muya@gmail.com", "Muya@1", "Female", "Muya Fashion", "Mumbai");
    }
}
