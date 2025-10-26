package com.example.versicherung.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PremiumCalculator {
    public static BigDecimal mileageFactor(int mileage) {
        if (mileage <= 5000) return BigDecimal.valueOf(0.5);
        if (mileage <= 10000) return BigDecimal.valueOf(1.0);
        if (mileage <= 20000) return BigDecimal.valueOf(1.5);
        return BigDecimal.valueOf(2.0);
    }

    public static BigDecimal vehicleFactor(String type) {
        if (type == null) return BigDecimal.ONE;
        switch (type.toUpperCase()) {
            case "MOTORRAD": return BigDecimal.valueOf(0.8);
            case "PKW_SMALL": return BigDecimal.valueOf(1.0);
            case "PKW_MEDIUM": return BigDecimal.valueOf(1.2);
            case "SUV": return BigDecimal.valueOf(1.5);
            default: return BigDecimal.valueOf(1.0);
        }
    }

    public static BigDecimal regionFactor(String region) {
        if (region == null) return BigDecimal.valueOf(1.0);
        switch (region) {
            case "Bayern": return BigDecimal.valueOf(1.05);
            case "Berlin": return BigDecimal.valueOf(1.15);
            default: return BigDecimal.valueOf(1.0);
        }
    }

    public static BigDecimal calculate(int mileage, String vehicleType, String region) {
        BigDecimal m = mileageFactor(mileage);
        BigDecimal v = vehicleFactor(vehicleType);
        BigDecimal r = regionFactor(region);
        return m.multiply(v).multiply(r).setScale(2, RoundingMode.HALF_UP);
    }
}
