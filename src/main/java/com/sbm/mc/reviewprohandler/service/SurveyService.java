package com.sbm.mc.reviewprohandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

@Service
public class SurveyService {

    private static final Logger logger = LoggerFactory.getLogger(SurveyService.class);

    private final RestTemplate restTemplate;

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    public SurveyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RvpApiSurvey> getSurveys() {
        String responseBody = null;
        List<RvpApiSurvey> surveys = null;
        String url = baseUrl + "/v1/surveys";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            responseBody = response.getBody();
            logger.info("Successfully retrieved surveys: {}", responseBody);
            surveys = mapToSurvey(responseBody);
            for (RvpApiSurvey survey : surveys) {
                logger.debug(survey.toString());
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error("Client/Server error while fetching surveys: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error while fetching surveys", ex);
            throw new RuntimeException("Unexpected error occurred while fetching surveys", ex);
        }
        return surveys;
    }

    public List<String> extractSurveyIds() {
        List<RvpApiSurvey> surveysList = getSurveys();

        List<String> ids = new ArrayList<>();

        for (RvpApiSurvey rvpApiSurvey : surveysList) {
            ids.add(rvpApiSurvey.getId());
        }

        logger.info("Surveys Ids : " + Arrays.toString(ids.toArray()));
        return ids;
    }

    public List<RvpApiSurvey> mapToSurvey(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<RvpApiSurvey> surveys = null;
        try {
            surveys = mapper.readValue(json, new TypeReference<List<RvpApiSurvey>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return surveys;
    }

    public boolean pidAndSurveyMap(String pid, String surveyId, List<RvpApiSurvey> surveys) {
        HashMap<String, String> surveyAndPids = new HashMap<>();
        for (RvpApiSurvey survey : surveys) {
            surveyAndPids.put(survey.getId(), survey.getPids());
        }
        if (surveyAndPids.get(surveyId).contains(pid)) {
            logger.debug("SurveyId " + surveyId + " is used for pid : " + pid);
            return true;
        }
        logger.debug("SurveyId " + surveyId + " is not used for pid : " + pid);
        return false;
    }
}
