package com.example.model;

import java.util.List;

public class Cart {
	private List<CartItem> items;
	
	public Cart() {
		
	}
	
	public Cart(List<CartItem> items) {
		this.items = items;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	
	public double getTotalValue() {
		return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}
	
	public int getTotalItemsCount() {
		return items.stream().mapToInt(CartItem::getQuantity).sum();
	}

}
