package com.example.versicherung.service;

import com.example.versicherung.dto.ApplicationRequest;
import com.example.versicherung.dto.ApplicationResponse;
import com.example.versicherung.model.Application;
import com.example.versicherung.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ApplicationService {
    private final ApplicationRepository repository;
    private final RegionService regionService;

    public ApplicationService(ApplicationRepository repository, RegionService regionService) {
        this.repository = repository;
        this.regionService = regionService;
    }

    public ApplicationResponse create(ApplicationRequest req) {
        var record = regionService.findByPostcode(req.getPostcode());
        String region = record != null ? record.getRegion1() : null;

        BigDecimal mFactor = PremiumCalculator.mileageFactor(req.getMileage());
        BigDecimal vFactor = PremiumCalculator.vehicleFactor(req.getVehicleType());
        BigDecimal rFactor = PremiumCalculator.regionFactor(region);
        BigDecimal premium = PremiumCalculator.calculate(req.getMileage(), req.getVehicleType(), region);

        Application app = new Application();
        app.setMileage(req.getMileage());
        app.setVehicleType(req.getVehicleType());
        app.setPostcode(req.getPostcode());
        app.setRegion(region);
        app.setMileageFactor(mFactor);
        app.setVehicleFactor(vFactor);
        app.setRegionFactor(rFactor);
        app.setPremium(premium);
        app.setCreatedAt(Instant.now());

        app = repository.save(app);
        return toResponse(app);
    }

    public ApplicationResponse getById(UUID id) {
        var app = repository.findById(id).orElseThrow(() -> new NotFoundException("Application not found"));
        return toResponse(app);
    }

    public java.util.Map<String, Object> calculate(ApplicationRequest req) {
        var record = regionService.findByPostcode(req.getPostcode());
        String region = record != null ? record.getRegion1() : null;

        java.math.BigDecimal mFactor = PremiumCalculator.mileageFactor(req.getMileage());
        java.math.BigDecimal vFactor = PremiumCalculator.vehicleFactor(req.getVehicleType());
        java.math.BigDecimal rFactor = PremiumCalculator.regionFactor(region);
        java.math.BigDecimal premium = PremiumCalculator.calculate(req.getMileage(), req.getVehicleType(), region);

        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("mileageFactor", mFactor);
        result.put("vehicleFactor", vFactor);
        result.put("regionFactor", rFactor);
        result.put("region", region);
        result.put("premium", premium);
        return result;
    }

    private ApplicationResponse toResponse(Application app) {
        ApplicationResponse r = new ApplicationResponse();
        r.setId(app.getId());
        r.setMileage(app.getMileage());
        r.setVehicleType(app.getVehicleType());
        r.setPostcode(app.getPostcode());
        r.setRegion(app.getRegion());
        r.setMileageFactor(app.getMileageFactor());
        r.setVehicleFactor(app.getVehicleFactor());
        r.setRegionFactor(app.getRegionFactor());
        r.setPremium(app.getPremium());
        r.setCreatedAt(app.getCreatedAt());
        return r;
    }
}
