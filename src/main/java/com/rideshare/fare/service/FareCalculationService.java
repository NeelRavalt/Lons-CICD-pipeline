package com.rideshare.fare.service;

import com.rideshare.fare.model.RideRequest;

public class FareCalculationService {

    private static final double BASE_FARE = 50;
    private static final double PER_KM_RATE = 10;
    private static final double PEAK_MULTIPLIER = 1.5;
    private static final double MAX_DISCOUNT_PERCENT = 50;

    private final DiscountService discountService;

    public FareCalculationService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public double calculateFare(RideRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        double distance = request.getDistanceKm();
        if (distance < 1 || distance > 100) {
            throw new IllegalArgumentException("Distance must be between 1 and 100 km");
        }

        double fare = BASE_FARE + (distance * PER_KM_RATE);

        if (request.isPeakHour()) {
            fare *= PEAK_MULTIPLIER;
        }

        double discountPercent = discountService.getDiscountPercentage(request.getPromoCode());
        discountPercent = Math.min(discountPercent, MAX_DISCOUNT_PERCENT);

        double discount = fare * (discountPercent / 100);
        return fare - discount;
    }
}
