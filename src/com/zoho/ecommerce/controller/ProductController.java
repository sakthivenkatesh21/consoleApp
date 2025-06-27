package com.zoho.ecommerce.controller;

import com.zoho.ecommerce.model.CardProduct;
import com.zoho.ecommerce.model.Category;
import com.zoho.ecommerce.model.Product;
import com.zoho.ecommerce.model.Seller;
import com.zoho.ecommerce.model.User;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private static int idGenerator;
    private final List<Product> products = DataManager.getDataManager().getProduct();

    // Creating product
    public Product createProduct(String productName, String productDescription, double price, int stock, Category category, User loggedInUser) {
        if (!products.isEmpty() && isProductExists(productName, loggedInUser) != null) {
            return null;
        }
        Product newProduct = new Product(++idGenerator, productName, productDescription, price, stock, category, (Seller) loggedInUser);
        products.add(newProduct);
        category.getProduct().add(newProduct);
        ((Seller) loggedInUser).getSellerProduct().add(newProduct);
        return newProduct;
    }

    // Updating product
    public boolean updateProduct(int id, String productName, String productDescription, double price, int stock) {
        Product product = isProductExist(id);
        if (product != null) {
            product.setProductName(productName);
            product.setDescription(productDescription);
            product.setPrice(price);
            product.setStock(stock);
            return true;
        }
        return false;
    }

    // Removing product
    public boolean removeProduct(Product product) {
        if (product != null) {
            products.remove(product);
            Category category = product.getCategory();
            if (category != null) {
                category.getProduct().remove(product);
                return true;
            }
        }
        return false;
    }

    // Helper methods for update
    public Product isProductExist(int productId) {
        for (Product obj : products) {
            if (obj.getId() == productId) {
                return obj;
            }
        }
        return null;
    }

    // Check if duplicate product exists for the same seller
    public Product isProductExists(String productName, User loggedInUser) {
        for (Product obj : products) {
            if (obj.getProductName().equals(productName) && obj.getSeller().getId() == loggedInUser.getId()) {
                return obj;
            }
        }
        return null;
    }

    public List<Product> isProductExists(String productName) {
        List<Product> searchProducts = new ArrayList<>();
        for (Product obj : products) {
            if (obj.getProductName().equals(productName)) {
                searchProducts.add(obj);
            }
        }
        return searchProducts.isEmpty() ? null : searchProducts;
    }

    // Remove product by category
    // This method will remove all products in the given category
    public boolean removeProductByCategory(List<Product> productList) {
        int removeCount = 0;
        int size = productList.size();
        for (Product obj : productList) {
            if (products.contains(obj)) {
                products.remove(obj);
                removeCount++;
            }
        }
        return removeCount == size;
    }

    // Get all products of a category for a seller
    // Only seller-added products will be returned
    public List<Product> getSellerProducts(Category category, User loggedInUser) {
        List<Product> sellerProducts = new ArrayList<>();
        for (Product obj : category.getProduct()) {
            if (obj.getSeller().getId() == ((Seller) loggedInUser).getId()) {
                sellerProducts.add(obj);
            }
        }
        return sellerProducts;
    }

    // Check if wishcard product contains duplicate or not
    public boolean isProductExistCard(List<CardProduct> product, int id) {
        for (CardProduct obj : product) {
            if (obj.getProduct().getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Get all products
    public List<Product> getProducts() {
        if (products.isEmpty()) {
            return null;
        }
        return products;
    }

    // Reduce stock of product after payment success
    public boolean reduceStock(List<CardProduct> cardProducts) {
        for (CardProduct obj : cardProducts) {
            Product product = isProductExist(obj.getProduct().getId());
            if (product == null) {
                return false;
            }
            product.setStock(product.getStock() - obj.getQuantity());
        }
        return true;
    }

    // Check if seller product is out of stock
    public boolean getStockIsEmpty(User loggedInUser) {
        for (Product product : products) {
            if (product.getSeller().getId() == loggedInUser.getId() && product.isAvailableStock()) {
                return false;
            }
        }
        return true;
    }

    // Returning the seller out-of-stock products
    public List<Product> getEmptyStockProducts(User loggedInUser) {
        List<Product> emptyStockProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getSeller().getId() == loggedInUser.getId() && product.isAvailableStock()) {
                emptyStockProducts.add(product);
            }
        }
        return emptyStockProducts;
    }
}
