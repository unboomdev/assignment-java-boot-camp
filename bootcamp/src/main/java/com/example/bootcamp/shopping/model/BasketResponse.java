package com.example.bootcamp.shopping.model;

import com.example.bootcamp.product.entities.Product;

import java.util.List;

public class BasketResponse {
    private int totalPrice;
    private List<BasketItem> products;

    public BasketResponse(int totalPrice, List<BasketItem> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<BasketItem> getProducts() {
        return products;
    }

    public void setProducts(List<BasketItem> products) {
        this.products = products;
    }
}
