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

@Service
public class GlobalReviewService {

    private static final Logger logger = LoggerFactory.getLogger(GlobalReviewService.class);

    private final RestTemplate restTemplate;

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    public GlobalReviewService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGlobalReviewData(String surveyId, Long pid, String fromDate, String toDate) {
        String url = baseUrl + "/v1/surveys/" + surveyId + "/score/nps";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        StringBuilder query = new StringBuilder();
        query.append("?pid=").append(pid);
        putIfNotNull(query, "fd", fromDate);
        putIfNotNull(query, "td", toDate);

        url += query.toString();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            logger.info("Making request to URL: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Received response: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            throw e;
        }
    }

    public void putIfNotNull(StringBuilder queryString, String key, String value) {
        if (value != null) {
            queryString.append("&").append(key).append("=").append(value);
        }
    }
}
