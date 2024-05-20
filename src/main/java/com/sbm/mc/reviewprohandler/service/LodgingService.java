package com.sbm.mc.reviewprohandler.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LodgingService {

    private static final Logger logger = LoggerFactory.getLogger(LodgingService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${reviewpro.api.url}")
    private String baseUrl;

    @Value("${reviewpro.api.key}")
    private String apiKey;

    public ResponseEntity<String> getLodgings() {
        String url = baseUrl + "/v1/account/lodgings";
        logger.debug("Lodgings URL : " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Making the request using RestTemplate
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Successfully retrieved lodgings: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while making API call", e);
            throw new RuntimeException("Failed to retrieve lodging details", e);
        }
    }

    public List<String> getLodgingIds() {
        String jsonString = getLodgings().getBody();
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
        logger.info("Lodgings Ids : " + Arrays.toString(ids.toArray()));
        return ids;
    }
}
