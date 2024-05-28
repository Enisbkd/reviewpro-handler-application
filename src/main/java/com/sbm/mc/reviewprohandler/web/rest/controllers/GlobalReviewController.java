package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import com.sbm.mc.reviewprohandler.service.GlobalReviewService;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
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
public class GlobalReviewController {

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

    @GetMapping("/globalReview")
    public RvpApiGlobalReview getNps(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        return globalReviewService.getGlobalReview(pid, fd, td);
    }

    @GetMapping("/getAllGlobalReviews")
    public List<RvpApiGlobalReview> getAllSurveysNps(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiGlobalReview> globalReviews = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            globalReviews.add(getNps(Integer.parseInt(logingId), fd, td));
        }
        sendToKafka(globalReviews);
        return globalReviews;
    }

    private void sendToKafka(List<RvpApiGlobalReview> globalReviews) {
        for (RvpApiGlobalReview globalReview : globalReviews) {
            ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(globalReview);
                kafkaProducerService.sendToKafka(json, globalReview.getId().toString(), "data-reviewpro-global-review");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
