package com.example.controller;

import com.example.model.BestCouponRequest;
import com.example.model.Coupon;
import com.example.model.CouponResponse;
import com.example.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	//Create a new Coupon
	@PostMapping
	public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Coupon created successfully");
            response.put("coupon", createdCoupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
	
	//Get best coupon for user and cart
	@PostMapping("/best")
    public ResponseEntity<?> getBestCoupon(@RequestBody BestCouponRequest request) {
        CouponResponse bestCoupon = couponService.findBestCoupon(request);
        
        if (bestCoupon == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No applicable coupon found");
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.ok(bestCoupon);
    }

}
