package com.example.versicherung.service;

import com.example.versicherung.service.RegionService;
import com.example.versicherung.service.PostcodeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegionServiceTest {

    @Autowired
    RegionService regionService;

    @Test
    void findsKnownPostcode() {
        PostcodeRecord r = regionService.findByPostcode("79189");
        assertNotNull(r, "Expected a PostcodeRecord for 79189");
        assertEquals("79189", r.getPostcode());
    }
}
