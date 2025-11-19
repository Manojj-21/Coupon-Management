package com.example.model;

public class CartItem {
	private String productId;
	private String category;
	private double price;
	private int quantity;
	
	public CartItem() {
		
	}

	public CartItem(String productId, String category, double price, int quantity) {
		super();
		this.productId = productId;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	

}
