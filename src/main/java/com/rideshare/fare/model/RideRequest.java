package com.rideshare.fare.model;

public class RideRequest {
    private double distanceKm;
    private boolean isPeakHour;
    private String promoCode;

    public RideRequest(double distanceKm, boolean isPeakHour, String promoCode) {
        this.distanceKm = distanceKm;
        this.isPeakHour = isPeakHour;
        this.promoCode = promoCode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public boolean isPeakHour() {
        return isPeakHour;
    }

    public String getPromoCode() {
        return promoCode;
    }
}
