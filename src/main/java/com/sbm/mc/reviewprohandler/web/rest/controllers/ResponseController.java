package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import com.sbm.mc.reviewprohandler.service.ResponseService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResponseController {

    @Value("${spring.kafka.topics.responses-topic}")
    private String responsesTopic;

    private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

    private final SurveyService surveyService;
    private final LodgingService lodgingService;
    private final ResponseService responseService;
    private final KafkaProducerService kafkaProducerService;
    private final List<RvpApiSurvey> surveys;

    public ResponseController(
        SurveyService surveyService,
        LodgingService lodgingService,
        ResponseService responseService,
        KafkaProducerService kafkaProducerService
    ) {
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
        this.responseService = responseService;
        this.kafkaProducerService = kafkaProducerService;
        this.surveys = surveyService.getSurveys();
    }

    @GetMapping("/responses-by-pid-and-surveyid")
    public List<RvpApiResponse> getSurveyResponses(
        @RequestParam String surveyId,
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        List<RvpApiResponse> responses = new ArrayList<>();
        if (surveyService.pidAndSurveyMap(String.valueOf(pid), surveyId, surveys)) {
            logger.debug("Getting Responses with match : ");
            responses = responseService.getSurveyResponsesWithPagination(surveyId, pid, fd, td, flagged, onlyPublished, dateField);
            sendToKafka(responses);
        }
        return responses;
    }

    @GetMapping("/responses-by-pid")
    public List<RvpApiResponse> getResponsesByPid(
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField,
        @RequestParam(required = false) List<String> surveyIds
    ) {
        if (surveyIds == null) {
            surveyIds = surveyService.extractSurveyIds();
        }
        List<RvpApiResponse> allPidResponses = new ArrayList<>();
        String response = null;
        for (String surveyId : surveyIds) {
            List<RvpApiResponse> responses = getSurveyResponses(surveyId, pid, fd, td, flagged, onlyPublished, dateField);
            logger.info("Response for pid : " + pid + " on survey: " + surveyId + " :: " + response);
            allPidResponses.addAll(responses);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return allPidResponses;
    }

    @GetMapping("/responses")
    public ResponseEntity<String> getAllResponses(
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        List<String> surveyIds = surveyService.extractSurveyIds();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<RvpApiResponse> responses = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            responses.addAll(getResponsesByPid(Integer.parseInt(logingId), fd, td, flagged, onlyPublished, dateField, surveyIds));
        }
        return new ResponseEntity<>("Returned " + responses.size() + " Reviewpro responses.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApiResponse> responses) {
        for (RvpApiResponse response : responses) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(response);
                kafkaProducerService.sendToKafka(json, response.getSurveyId(), responsesTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
