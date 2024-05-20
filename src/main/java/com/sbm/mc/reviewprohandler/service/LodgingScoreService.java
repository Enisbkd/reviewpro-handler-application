package com.sbm.mc.reviewprohandler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LodgingScoreService {

    private static final Logger logger = LoggerFactory.getLogger(LodgingScoreService.class);

    private final RestTemplate restTemplate;

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    public LodgingScoreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLodgingDetails(int pid, String fd, String td) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .pathSegment("reviewpro", "v1", "v1", "lodging", "source", "detail", "product")
            .queryParam("pid", pid)
            .queryParam("fd", fd)
            .queryParam("td", td)
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        logger.info("Making request to URL: {}", url);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            logger.info("Response received: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred while making API call", e);
            throw new RuntimeException("Failed to retrieve lodging details", e);
        }
    }
}
