package com.example.versicherung.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegionService {
    private final Map<String, PostcodeRecord> map = new HashMap<>();

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("postcodes.csv").getInputStream(), StandardCharsets.UTF_8))) {
            // read and ignore header line if present
            String header = reader.readLine();

            // Use Apache Commons CSV to correctly parse quoted fields and embedded commas
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withQuote('"').withTrim());
            for (CSVRecord rec : parser) {
                // Guard for shorter lines
                String post = rec.size() > 6 ? rec.get(6) : "";
                String region1 = rec.size() > 2 ? rec.get(2) : "";
                if (post != null) post = post.replaceAll("\"", "").trim();
                if (region1 != null) region1 = region1.replaceAll("\"", "").trim();
                if (post != null && !post.isEmpty()) {
                    map.put(post, new PostcodeRecord(post, region1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load postcodes.csv", e);
        }
    }

    public PostcodeRecord findByPostcode(String postcode) {
        if (postcode == null) return null;
        String p = postcode.trim();
        return map.get(p);
    }
}
