package src.com.zoho.ecommerce.controller;

import src.com.zoho.ecommerce.model.Category;
import java.util.List;

public class CategoryController {
    private static int idGenerator;
    private final List<Category> category = DataManager.getDataManager().getCategory();

    // Check if category list is empty
    public boolean isCategoryEmpty() {
        return category.isEmpty();
    }

    // Check if duplicate category exists by name
    public boolean isCategoryExists(String categoryName) {
        for (Category obj : category) {
            if (obj.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    // Get all categories
    public List<Category> getCategories() {
        return category;
    }

    // Create a new category
    public Category createCategory(String categoryName, String categoryDescription) {
        if (isCategoryExists(categoryName)) {
            return null;
        }
        Category newCategory = new Category(++idGenerator, categoryName, categoryDescription);
        category.add(newCategory);
        return newCategory;
    }

    // Remove category and its products
    public boolean removeCategory(Category obj) {
        if (obj != null) {
            category.remove(obj);
            if (obj.getProduct() == null || obj.getProduct().isEmpty()) {
                return true;
            }
            return new ProductController().removeProductByCategory(obj.getProduct());
        }
        return false;
    }
}
