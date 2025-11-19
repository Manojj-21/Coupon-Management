package com.example.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.example.model.Coupon;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepository {
	// In-memory storage for coupons
    private final Map<String, Coupon> coupons = new ConcurrentHashMap<>();
    
    // Track usage per user per coupon
    private final Map<String, Map<String, Integer>> usageTracker = new ConcurrentHashMap<>();
 // Save a coupon
    public Coupon save(Coupon coupon) {
        coupons.put(coupon.getCode(), coupon);
        return coupon;
    }

    // Get all coupons
    public List<Coupon> findAll() {
        return new ArrayList<>(coupons.values());
    }

    // Find coupon by code
    public Optional<Coupon> findByCode(String code) {
        return Optional.ofNullable(coupons.get(code));
    }

    // Check if coupon exists
    public boolean existsByCode(String code) {
        return coupons.containsKey(code);
    }

    // Get usage count for a user and coupon
    public int getUsageCount(String userId, String couponCode) {
        return usageTracker
                .getOrDefault(userId, new HashMap<>())
                .getOrDefault(couponCode, 0);
    }

    // Increment usage count
    public void incrementUsage(String userId, String couponCode) {
        usageTracker
                .computeIfAbsent(userId, k -> new HashMap<>())
                .merge(couponCode, 1, Integer::sum);
    }
}
