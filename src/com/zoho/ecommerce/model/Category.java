package com.zoho.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String name;
    private String description;
    private List<Product> product;

    public Category() {
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.product = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        System.out.println("Category ID : " + id);
        System.out.println("Category Name : " + name);
        System.out.println("Category Description : " + description);
        return "";
    }
}
