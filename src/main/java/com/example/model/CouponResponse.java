package com.example.model;

public class CouponResponse {
	private String code;
	private String description;
	private double discountAmount;
	private double finalCartValue;
	
	public CouponResponse() {
		
	}

	public CouponResponse(String code, String description, double discountAmount, double finalCartValue) {
		this.code = code;
		this.description = description;
		this.discountAmount = discountAmount;
		this.finalCartValue = finalCartValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getFinalCartValue() {
		return finalCartValue;
	}

	public void setFinalCartValue(double finalCartValue) {
		this.finalCartValue = finalCartValue;
	}
	
	
	
	
}