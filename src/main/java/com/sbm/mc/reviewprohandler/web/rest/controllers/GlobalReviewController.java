package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import com.sbm.mc.reviewprohandler.service.GlobalReviewService;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
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
public class GlobalReviewController {

    @Value("${spring.kafka.topics.global-reviews-topic}")
    private String grTopic;

    private static final Logger logger = LoggerFactory.getLogger(GlobalReviewController.class);

    private final GlobalReviewService globalReviewService;
    private final LodgingService lodgingService;
    private final KafkaProducerService kafkaProducerService;

    public GlobalReviewController(
        GlobalReviewService globalReviewService,
        LodgingService lodgingService,
        KafkaProducerService kafkaProducerService
    ) {
        this.globalReviewService = globalReviewService;
        this.lodgingService = lodgingService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/global-review-by-pid")
    public RvpApiGlobalReview getNps(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        return globalReviewService.getGlobalReview(pid, fd, td);
    }

    @GetMapping("/global-reviews")
    public ResponseEntity<String> getAllSurveysNps(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiGlobalReview> globalReviews = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            globalReviews.add(getNps(Integer.parseInt(logingId), fd, td));
        }
        logger.info("Sending " + globalReviews.size() + " to topic " + "");
        sendToKafka(globalReviews);
        return new ResponseEntity<>("Returned " + globalReviews.size() + " Reviewpro global reviews.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApiGlobalReview> globalReviews) {
        for (RvpApiGlobalReview globalReview : globalReviews) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();

            try {
                String bytes = ow.writeValueAsString(globalReview);
                kafkaProducerService.sendToKafka(bytes, globalReview.getId().toString(), grTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
