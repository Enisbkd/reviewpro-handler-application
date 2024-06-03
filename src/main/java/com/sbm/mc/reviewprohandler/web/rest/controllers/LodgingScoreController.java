package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScore;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingScoreController {

    @Value("${spring.kafka.topics.lodging-scores-topic}")
    private String lodgingScoresTopic;

    private final LodgingScoreService lodgingScoreService;
    private final SurveyService surveyService;
    private final LodgingService lodgingService;
    private final KafkaProducerService kafkaProducerService;

    public LodgingScoreController(
        LodgingScoreService lodgingScoreService,
        SurveyService surveyService,
        LodgingService lodgingService,
        KafkaProducerService kafkaProducerService
    ) {
        this.lodgingScoreService = lodgingScoreService;
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/lodging-score-by-pid-and-surveyid")
    public RvpApiLodgingScore getLodgingDetails(
        @RequestParam String surveyId,
        @RequestParam Integer pid,
        @RequestParam String fd,
        @RequestParam String td
    ) {
        return lodgingScoreService.getLodgingScore(surveyId, pid, fd, td);
    }

    @GetMapping("/lodging-scores-by-pid")
    public List<RvpApiLodgingScore> getLodgingScoresByPid(
        @RequestParam Integer pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam(required = false) List<String> surveyIds
    ) {
        List<RvpApiLodgingScore> rvpApiLodgingScoresbyPid = new ArrayList<>();
        if (surveyIds == null) {
            surveyIds = surveyService.extractSurveyIds();
        }

        for (String surveyId : surveyIds) {
            rvpApiLodgingScoresbyPid.add(lodgingScoreService.getLodgingScore(surveyId, pid, fd, td));
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return rvpApiLodgingScoresbyPid;
    }

    @GetMapping("/lodging-scores")
    public ResponseEntity<String> getAllLodgingScores(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingScore> allLodgingScores = new ArrayList<>();
        List<String> surveyIds = surveyService.extractSurveyIds();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<String> lodgingIds = lodgingService.getLodgingIds();
        for (String lodgingId : lodgingIds) {
            allLodgingScores.addAll(getLodgingScoresByPid(Integer.valueOf(lodgingId), fd, td, surveyIds));
        }

        sendToKafka(allLodgingScores);

        return new ResponseEntity<>("Returned " + allLodgingScores.size() + " Reviewpro lodging scores.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApiLodgingScore> allLodgingScores) {
        for (RvpApiLodgingScore lodgingScore : allLodgingScores) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(lodgingScore);
                kafkaProducerService.sendToKafka(json, lodgingScore.getId().toString(), lodgingScoresTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
