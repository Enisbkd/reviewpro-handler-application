package com.sbm.mc.reviewprohandler.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    public String getSurveys() {
        String responseBody = null;
        String url = baseUrl + "/v1/surveys";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            responseBody = response.getBody();
            logger.info("Successfully retrieved surveys: {}", responseBody);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error("Client/Server error while fetching surveys: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error while fetching surveys", ex);
            throw new RuntimeException("Unexpected error occurred while fetching surveys", ex);
        }
        return responseBody;
    }

    public List<String> extractSurveyIds() {
        String jsonString = getSurveys();

        List<String> ids = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            String id = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getString("id");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            ids.add(id);
        }
        logger.info("Surveys Ids : " + Arrays.toString(ids.toArray()));
        return ids;
    }
}
