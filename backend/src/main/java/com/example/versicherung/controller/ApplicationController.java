package com.example.versicherung.controller;

import com.example.versicherung.dto.ApplicationRequest;
import com.example.versicherung.dto.ApplicationResponse;
import com.example.versicherung.service.ApplicationService;
import com.example.versicherung.service.PremiumCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ApplicationController {
    private final ApplicationService service;

    public ApplicationController(ApplicationService service) { this.service = service; }

    @PostMapping("/applications")
    public ResponseEntity<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest req) {
        ApplicationResponse r = service.create(req);
        return ResponseEntity.created(URI.create("/api/applications/" + r.getId())).body(r);
    }

    @GetMapping("/applications/{id}")
    public ApplicationResponse get(@PathVariable java.util.UUID id) {
        return service.getById(id);
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@Valid @RequestBody ApplicationRequest req) {
        var result = service.calculate(req);
        return ResponseEntity.ok(result);
    }
}
