package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SurveyController {

    private final SurveyService surveyService;
    private final KafkaProducerService kafkaProducerService;

    public SurveyController(SurveyService surveyService, KafkaProducerService kafkaProducerService) {
        this.surveyService = surveyService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/surveys")
    public List<RvpApiSurvey> getSurveys() {
        List<RvpApiSurvey> surveys = surveyService.getSurveys();

        sendToKafka(surveys);

        return surveys;
    }

    private void sendToKafka(List<RvpApiSurvey> surveys) {
        for (RvpApiSurvey survey : surveys) {
            ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(survey);
                kafkaProducerService.sendToKafka(json, survey.getId(), "data-reviewpro-surveys");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
