package com.example.versicherung.service;

public class PostcodeRecord {
    private final String postcode;
    private final String region1;

    public PostcodeRecord(String postcode, String region1) {
        this.postcode = postcode;
        this.region1 = region1;
    }

    public String getPostcode() { return postcode; }
    public String getRegion1() { return region1; }
}
