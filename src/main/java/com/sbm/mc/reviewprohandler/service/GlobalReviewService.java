package com.sbm.mc.reviewprohandler.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import java.io.IOException;
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
import org.springframework.web.util.UriComponentsBuilder;

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

    public RvpApiGlobalReview getGlobalReview(int pid, String fd, String td) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .pathSegment("v1", "lodging", "source", "detail", "product")
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
            return mapToGlobalReviews(response.getBody(), fd, td);
        } catch (Exception e) {
            logger.error("Error occurred while making API call", e);
            throw new RuntimeException("Failed to retrieve lodging details", e);
        }
    }

    public RvpApiGlobalReview mapToGlobalReviews(String json, String fd, String td) {
        ObjectMapper objectMapper = new ObjectMapper();
        RvpApiGlobalReview rvpApiGlobalReview = new RvpApiGlobalReview();

        try {
            logger.info("Json ::: " + json);
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode productNode = rootNode.get("product");

            int pid = productNode.get("pid").asInt();
            double prevGri = productNode.get("prevGri").asDouble();
            String distribution = objectMapper.writeValueAsString(productNode.get("distribution"));
            double gri = productNode.get("gri").asDouble();

            rvpApiGlobalReview.setFd(LocalDate.parse(fd));
            rvpApiGlobalReview.setTd(LocalDate.parse(td));
            rvpApiGlobalReview.setLodgingid(pid);
            rvpApiGlobalReview.setGri(gri);
            rvpApiGlobalReview.setPrevGri(prevGri);
            rvpApiGlobalReview.setDistribution(distribution.toString());

            System.out.println(rvpApiGlobalReview);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rvpApiGlobalReview;
    }
}
