package com.sbm.mc.reviewprohandler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class LodgingCqiService {

    private static final Logger logger = LoggerFactory.getLogger(LodgingCqiService.class);

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LodgingCqiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLodgingIndex(String pid, String fd, String td) {
        String urlTemplate = baseUrl + "/v1/lodging/index/cqi";

        String url = String.format("%s?pid=%s&fd=%s&td=%s", urlTemplate, pid, fd, td);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            logger.info("Making API call to URL: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("API call successful, status code: {}", response.getStatusCode());
                return response.getBody();
            } else {
                logger.error("API call failed, status code: {}", response.getStatusCode());
                throw new RuntimeException("Failed to fetch data from API");
            }
        } catch (RestClientException e) {
            logger.error("Exception occurred while making API call", e);
            throw new RuntimeException("Exception occurred while making API call", e);
        }
    }
}
