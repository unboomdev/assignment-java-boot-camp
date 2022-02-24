package com.example.bootcamp.shopping.model;

import javax.validation.constraints.NotNull;

public class BasketRequest {
    @NotNull(message = "userId is null")
    private Integer userId;
    @NotNull(message = "productId is null")
    private Integer productId;
    @NotNull(message = "quantity is null")
    private Integer quantity;

    public BasketRequest(Integer userId, Integer productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
