package com.zoho.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private List<CardProduct> product;

    public Card() {
        product = new ArrayList<>();
    }

    public Card(List<CardProduct> product) {
        this.product = product;
    }

    public List<CardProduct> getProduct() {
        return product;
    }

    public void setProduct(List<CardProduct> product) {
        this.product = product;
    }

}
