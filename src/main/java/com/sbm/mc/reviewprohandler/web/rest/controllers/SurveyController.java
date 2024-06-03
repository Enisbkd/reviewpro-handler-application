package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SurveyController {

    @Value("${spring.kafka.topics.surveys-topic}")
    private String surveysTopic;

    private final SurveyService surveyService;
    private final KafkaProducerService kafkaProducerService;

    public SurveyController(SurveyService surveyService, KafkaProducerService kafkaProducerService) {
        this.surveyService = surveyService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/surveys")
    public ResponseEntity<String> getSurveys() {
        List<RvpApiSurvey> surveys = surveyService.getSurveys();

        sendToKafka(surveys);

        return new ResponseEntity<>("Returned " + surveys.size() + " Reviewpro surveys.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApiSurvey> surveys) {
        for (RvpApiSurvey survey : surveys) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(survey);
                kafkaProducerService.sendToKafka(json, survey.getId(), surveysTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
