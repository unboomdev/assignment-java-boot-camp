package com.example.bootcamp.product.model;

import java.util.List;

public class ProductSearchResponse{
	private List<ProductsItem> products;
	private int total;

	public ProductSearchResponse(List<ProductsItem> products, int total) {
		this.products = products;
		this.total = total;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}
}