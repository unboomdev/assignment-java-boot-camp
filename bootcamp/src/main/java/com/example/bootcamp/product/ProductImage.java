package com.example.bootcamp.product;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductImage {
    @Id
    private int id;
    private String url;
    private int productId;

    public ProductImage() {
    }

    public ProductImage(int id, String url, int productId) {
        this.id = id;
        this.url = url;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
