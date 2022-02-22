package com.example.bootcamp.product.model;

import java.util.List;

public class ProductResponse{
	private int id;
	private String name;
	private int price;
	private int discount;
	private String description;
	private List<String> images;

	public ProductResponse() {
	}

	public ProductResponse(int id, String name, int price, int discount, String description, List<String> images) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.description = description;
		this.images = images;
	}

	public void setImages(List<String> images){
		this.images = images;
	}

	public List<String> getImages(){
		return images;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDiscount(int discount){
		this.discount = discount;
	}

	public int getDiscount(){
		return discount;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}