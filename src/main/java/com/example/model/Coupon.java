package com.example.model;

import java.time.LocalDate;

public class Coupon {
	private String code;
	private String description;
	private String discountType;
	private double discountValue;
	private Double maxDiscountAmount;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer usageLimitPerUser;
	private Eligibility eligibility;
	
	
	
	public Coupon() {
		
	}

	public Coupon(String code, String description, String discountType, double discountValue, Double maxDiscountAmount,
			LocalDate startDate, LocalDate endDate, Integer usageLimitPerUser, Eligibility eligibility) {
		super();
		this.code = code;
		this.description = description;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.maxDiscountAmount = maxDiscountAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.usageLimitPerUser = usageLimitPerUser;
		this.eligibility = eligibility;
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

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public Double getMaxDiscountAmount() {
		return maxDiscountAmount;
	}

	public void setMaxDiscountAmount(Double maxDiscountAmount) {
		this.maxDiscountAmount = maxDiscountAmount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getUsageLimitPerUser() {
		return usageLimitPerUser;
	}

	public void setUsageLimitPerUser(Integer usageLimitPerUser) {
		this.usageLimitPerUser = usageLimitPerUser;
	}

	public Eligibility getEligibility() {
		return eligibility;
	}

	public void setEligibility(Eligibility eligibility) {
		this.eligibility = eligibility;
	}
	
	
	
	

}
