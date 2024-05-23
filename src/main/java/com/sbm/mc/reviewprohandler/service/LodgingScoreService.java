package com.sbm.mc.reviewprohandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScore;
import java.time.LocalDate;
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

    public RvpApiLodgingScore getLodgingScore(String surveyId, Integer pid, String fromDate, String toDate) {
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
            return mapToLodgingScore(response.getBody(), surveyId, pid, fromDate, toDate);
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            throw e;
        }
    }

    public RvpApiLodgingScore mapToLodgingScore(String json, String surveyId, Integer pid, String fromDate, String toDate) {
        ObjectMapper mapper = new ObjectMapper();
        RvpApiLodgingScore lodgingScore = new RvpApiLodgingScore();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(json);
            Double nps = rootNode.get(pid.toString()) != null ? rootNode.get(pid.toString()).asDouble() : null;
            lodgingScore.setNps(nps);
            lodgingScore.setLodgingId(Integer.valueOf(pid));
            lodgingScore.setFd(LocalDate.parse(fromDate));
            lodgingScore.setTd(LocalDate.parse(toDate));
            lodgingScore.setSurveyId(surveyId);

            String keyToHash = surveyId + pid + fromDate + toDate;
            int keyhash = keyToHash.hashCode();

            lodgingScore.setId(keyhash);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return lodgingScore;
    }

    public void putIfNotNull(StringBuilder queryString, String key, String value) {
        if (value != null) {
            queryString.append("&").append(key).append("=").append(value);
        }
    }
}
