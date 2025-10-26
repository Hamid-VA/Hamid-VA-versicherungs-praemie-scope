package com.example.versicherung.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PremiumCalculatorTest {

    @Test
    void mileageFactorRanges() {
        assertEquals(0, PremiumCalculator.mileageFactor(0).compareTo(BigDecimal.valueOf(0.5)));
        assertEquals(0, PremiumCalculator.mileageFactor(5000).compareTo(BigDecimal.valueOf(0.5)));
        assertEquals(0, PremiumCalculator.mileageFactor(5001).compareTo(BigDecimal.valueOf(1.0)));
        assertEquals(0, PremiumCalculator.mileageFactor(10000).compareTo(BigDecimal.valueOf(1.0)));
        assertEquals(0, PremiumCalculator.mileageFactor(10001).compareTo(BigDecimal.valueOf(1.5)));
        assertEquals(0, PremiumCalculator.mileageFactor(20000).compareTo(BigDecimal.valueOf(1.5)));
        assertEquals(0, PremiumCalculator.mileageFactor(20001).compareTo(BigDecimal.valueOf(2.0)));
    }
}
