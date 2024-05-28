package com.sbm.mc.reviewprohandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public List<RvpApiResponse> getSurveyResponsesWithPagination(
        String surveyId,
        int pid,
        String fromDate,
        String toDate,
        String flagged,
        boolean onlyPublished,
        String dateField
    ) {
        List<RvpApiResponse> allResponses = new ArrayList<>();
        String firstResponse = getSurveyResponses(surveyId, pid, fromDate, toDate, flagged, onlyPublished, dateField, 0);
        allResponses.addAll(buildResponseObjects(null, surveyId, pid, fromDate, toDate, flagged, onlyPublished, dateField, 0));
        int totalResponses = getTotalResponses(firstResponse);
        logger.debug("Total responses : " + totalResponses);
        int callLimit = 100;
        if (totalResponses > callLimit) {
            int loopLimit = totalResponses / callLimit;
            for (int i = 1; i <= loopLimit; i++) {
                logger.debug("Paginating over responses: loop number : " + i);
                allResponses.addAll(
                    buildResponseObjects(null, surveyId, pid, fromDate, toDate, flagged, onlyPublished, dateField, i * callLimit)
                );
            }
            logger.debug("Responses parsed : " + allResponses.size());
        }
        return allResponses;
    }

    public String getSurveyResponses(
        String surveyId,
        int pid,
        String fromDate,
        String toDate,
        String flagged,
        boolean onlyPublished,
        String dateField,
        int offset
    ) {
        String urlTemplate = baseUrl + "/v3/surveys/{surveyId}/responses";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(urlTemplate)
            .queryParam("pid", pid)
            .queryParam("fd", fromDate)
            .queryParam("td", toDate)
            .queryParam("flagged", flagged)
            .queryParam("onlyPublished", onlyPublished ? 1 : 0)
            .queryParam("dateField", dateField)
            .queryParam("offset", offset);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String url = uriBuilder.buildAndExpand(surveyId).toUriString();
            logger.info("Sending request to URL: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Received response with status code: {}", response.getStatusCode());
            logger.info(response.getBody());
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

    public List<RvpApiResponse> buildResponseObjects(
        String jsonString,
        String surveyId,
        int pid,
        String fromDate,
        String toDate,
        String flagged,
        boolean onlyPublished,
        String dateField,
        int offset
    ) {
        if (jsonString == null) {
            jsonString = getSurveyResponses(surveyId, pid, fromDate, toDate, flagged, onlyPublished, dateField, offset);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<RvpApiResponse> responsesObjects = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            ArrayNode responsesNode = (ArrayNode) rootNode.get("responses");

            for (JsonNode responseNode : responsesNode) {
                Long date = responseNode.get("date").asLong();
                int productId = responseNode.get("productId").asInt();
                double customScoreValue = responseNode.get("customScore").get("value").asDouble();

                ArrayNode answersNode = (ArrayNode) responseNode.get("answers");
                List<String> labels = Arrays.asList("Revisite Bay", "Revisite HH", "Revisite BH", "Revisite HP");

                for (JsonNode answerNode : answersNode) {
                    String label = answerNode.get("label") == null ? "" : answerNode.get("label").asText();
                    int hashId = hashResponseId(productId, surveyId, fromDate, toDate, label);
                    RvpApiResponse response = new RvpApiResponse();

                    response.setId(hashId);
                    response.setDate(Instant.ofEpochMilli(date));
                    response.setLodgingId(productId);
                    response.setCustomScore(customScoreValue);
                    response.setSurveyId(answerNode.get("id").asText());
                    response.setLabel(label);
                    if (answerNode.get("label").asText().equalsIgnoreCase("Satisfaction globale")) {
                        response.setOverallsatsifaction(answerNode.get("answer").asDouble());
                    }
                    if (labels.contains(answerNode.get("label").asText())) {
                        response.setPlantorevisit(answerNode.get("answer").asText().equalsIgnoreCase("yes"));
                    }
                    responsesObjects.add(response);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return responsesObjects;
    }

    public int getTotalResponses(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            return rootNode.get("total").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int hashResponseId(int productId, String surveyId, String fromDate, String toDate, String label) {
        return (productId + surveyId + fromDate + toDate + label).hashCode();
    }
}
