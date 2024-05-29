package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import com.sbm.mc.reviewprohandler.service.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

    private final SurveyService surveyService;
    private final LodgingService lodgingService;
    private final ResponseService responseService;
    private final KafkaProducerService kafkaProducerService;

    public ResponseController(
        SurveyService surveyService,
        LodgingService lodgingService,
        ResponseService responseService1,
        LodgingScoreService lodgingScoreServiceA,
        KafkaProducerService kafkaProducerService
    ) {
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
        this.responseService = responseService1;
        this.kafkaProducerService = kafkaProducerService;
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
        List<RvpApiResponse> responses = responseService.getSurveyResponsesWithPagination(
            surveyId,
            pid,
            fd,
            td,
            flagged,
            onlyPublished,
            dateField
        );
        sendToKafka(responses);
        return responses;
    }

    @GetMapping("/responses-by-pid")
    public List<RvpApiResponse> getResponsesByPid(
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        List<String> surveyIds = surveyService.extractSurveyIds();
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
    public List<RvpApiResponse> getAllResponses(
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        List<RvpApiResponse> responses = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            responses.addAll(getResponsesByPid(Integer.parseInt(logingId), fd, td, flagged, onlyPublished, dateField));
        }
        return responses;
    }

    private void sendToKafka(List<RvpApiResponse> responses) {
        for (RvpApiResponse response : responses) {
            ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(response);
                kafkaProducerService.sendToKafka(json, response.getSurveyId(), "data-reviewpro-responses");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
