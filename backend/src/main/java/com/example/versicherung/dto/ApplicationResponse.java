package com.example.versicherung.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ApplicationResponse {
    private UUID id;
    private int mileage;
    private String vehicleType;
    private String postcode;
    private String region;
    private BigDecimal mileageFactor;
    private BigDecimal vehicleFactor;
    private BigDecimal regionFactor;
    private BigDecimal premium;
    private Instant createdAt;

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public BigDecimal getMileageFactor() { return mileageFactor; }
    public void setMileageFactor(BigDecimal mileageFactor) { this.mileageFactor = mileageFactor; }
    public BigDecimal getVehicleFactor() { return vehicleFactor; }
    public void setVehicleFactor(BigDecimal vehicleFactor) { this.vehicleFactor = vehicleFactor; }
    public BigDecimal getRegionFactor() { return regionFactor; }
    public void setRegionFactor(BigDecimal regionFactor) { this.regionFactor = regionFactor; }
    public BigDecimal getPremium() { return premium; }
    public void setPremium(BigDecimal premium) { this.premium = premium; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
