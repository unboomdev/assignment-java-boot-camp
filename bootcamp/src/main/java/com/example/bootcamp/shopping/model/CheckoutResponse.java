package com.example.bootcamp.shopping.model;

public class CheckoutResponse {
    private int orderId;
    private String message;

    public CheckoutResponse(int orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
