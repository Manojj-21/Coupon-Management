package com.example.service;

import com.example.model.*;
import com.example.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
	@Autowired
	private CouponRepository couponRepository;
	
	//create a new coupon
	public Coupon createCoupon(Coupon coupon) {
		if(couponRepository.existsByCode(coupon.getCode())) {
			throw new IllegalArgumentException("Coupon with code " + coupon.getCode() +" already exists");
		}
		return couponRepository.save(coupon);
	}
	
	//Find best coupon for user and cart
	public CouponResponse findBestCoupon(BestCouponRequest request) {
		UserContext user = request.getUser();
		Cart cart = request.getCart();
		
		List<Coupon> allCoupons = couponRepository.findAll();
		LocalDate today = LocalDate.now();
		
		//Filter eligible coupons
		List<Coupon> eligibleCoupons = allCoupons.stream()
				.filter(coupon -> isCouponValid(coupon, today))
				.filter(coupon -> hasNotExceededUsageLimit(user.getUserId(),coupon))
				.filter(coupon -> isEligible(coupon, user, cart))
				.collect(Collectors.toList());
		
		if(eligibleCoupons.isEmpty()) {
			return null;
		}
		
		// Calculate discount for each eligible coupon and find the best one
        Coupon bestCoupon = eligibleCoupons.stream()
                .max(Comparator
                        .comparingDouble((Coupon c) -> calculateDiscount(c, cart.getTotalValue()))
                        .thenComparing(Coupon::getEndDate)
                        .thenComparing(Comparator.comparing(Coupon::getCode).reversed()))
                .orElse(null);

        if (bestCoupon == null) {
            return null;
        }
        
        double discountAmount = calculateDiscount(bestCoupon, cart.getTotalValue());
        double finalCartValue = Math.max(0, cart.getTotalValue() - discountAmount);

        return new CouponResponse(
                bestCoupon.getCode(),
                bestCoupon.getDescription(),
                discountAmount,
                finalCartValue
        );
	}
	// Check if coupon is valid (date range)
    private boolean isCouponValid(Coupon coupon, LocalDate today) {
        return !today.isBefore(coupon.getStartDate()) && !today.isAfter(coupon.getEndDate());
    }

    // Check if user has not exceeded usage limit
    private boolean hasNotExceededUsageLimit(String userId, Coupon coupon) {
        if (coupon.getUsageLimitPerUser() == null) {
            return true; // No limit
        }
        int usageCount = couponRepository.getUsageCount(userId, coupon.getCode());
        return usageCount < coupon.getUsageLimitPerUser();
    }

    // Check eligibility based on user and cart
    private boolean isEligible(Coupon coupon, UserContext user, Cart cart) {
        Eligibility eligibility = coupon.getEligibility();
        if (eligibility == null) {
            return true; // No eligibility rules
        }

        // User-based checks
        if (eligibility.getAllowedUserTiers() != null && 
            !eligibility.getAllowedUserTiers().contains(user.getUserTier())) {
            return false;
        }

        if (eligibility.getMinLifetimeSpend() != null && 
            user.getLifetimeSpend() < eligibility.getMinLifetimeSpend()) {
            return false;
        }

        if (eligibility.getMinOrdersPlaced() != null && 
            user.getOrdersPlaced() < eligibility.getMinOrdersPlaced()) {
            return false;
        }

        if (Boolean.TRUE.equals(eligibility.getFirstOrderOnly()) && user.getOrdersPlaced() > 0) {
            return false;
        }

        if (eligibility.getAllowedCountries() != null && 
            !eligibility.getAllowedCountries().contains(user.getCountry())) {
            return false;
        }

        // Cart-based checks
        if (eligibility.getMinCartValue() != null && 
            cart.getTotalValue() < eligibility.getMinCartValue()) {
            return false;
        }

        if (eligibility.getMinItemsCount() != null && 
            cart.getTotalItemsCount() < eligibility.getMinItemsCount()) {
            return false;
        }

        if (eligibility.getApplicableCategories() != null && !eligibility.getApplicableCategories().isEmpty()) {
            boolean hasApplicableCategory = cart.getItems().stream()
                    .anyMatch(item -> eligibility.getApplicableCategories().contains(item.getCategory()));
            if (!hasApplicableCategory) {
                return false;
            }
        }

        if (eligibility.getExcludedCategories() != null && !eligibility.getExcludedCategories().isEmpty()) {
            boolean hasExcludedCategory = cart.getItems().stream()
                    .anyMatch(item -> eligibility.getExcludedCategories().contains(item.getCategory()));
            if (hasExcludedCategory) {
                return false;
            }
        }

        return true;
    }

    // Calculate discount amount
    private double calculateDiscount(Coupon coupon, double cartValue) {
        double discount;
        
        if ("FLAT".equalsIgnoreCase(coupon.getDiscountType())) {
            discount = coupon.getDiscountValue();
        } else if ("PERCENT".equalsIgnoreCase(coupon.getDiscountType())) {
            discount = (coupon.getDiscountValue() / 100.0) * cartValue;
        } else {
            return 0;
        }

        // Apply max discount cap if exists
        if (coupon.getMaxDiscountAmount() != null) {
            discount = Math.min(discount, coupon.getMaxDiscountAmount());
        }

        return discount;
    }

}
