package com.example.bootcamp.shopping.model;

public class BasketItemResponse {
    private int basketId;
    private String message;

    public BasketItemResponse(int basketId, String message) {
        this.basketId = basketId;
        this.message = message;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
