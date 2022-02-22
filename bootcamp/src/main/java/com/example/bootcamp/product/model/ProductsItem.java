package com.example.bootcamp.product.model;

public class ProductsItem{
	private int id;
	private String name;
	private int price;
	private int discount;
	private String imageUrl;

	public ProductsItem(int id, String name, int price, int discount, String imageUrl) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.imageUrl = imageUrl;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}
