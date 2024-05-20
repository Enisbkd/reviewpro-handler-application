package com.sbm.mc.reviewprohandler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ResponseService {

    private static final Logger logger = LoggerFactory.getLogger(ResponseService.class);

    private final RestTemplate restTemplate;

    public ResponseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    public String getSurveyResponses(
        String surveyId,
        int pid,
        String fromDate,
        String toDate,
        String flagged,
        boolean onlyPublished,
        String dateField
    ) {
        String urlTemplate = baseUrl + "/v3/surveys/{surveyId}/responses";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(urlTemplate)
            .queryParam("pid", pid)
            .queryParam("fd", fromDate)
            .queryParam("td", toDate)
            .queryParam("flagged", flagged)
            .queryParam("onlyPublished", onlyPublished ? 1 : 0)
            .queryParam("dateField", dateField);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String url = uriBuilder.buildAndExpand(surveyId).toUriString();
            logger.info("Sending request to URL: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Received response with status code: {}", response.getStatusCode());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("HTTP error occurred: {}", e.getStatusCode());
            logger.error("Error response body: {}", e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw e;
        }
    }
}
