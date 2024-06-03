package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingController {

    @Value("${spring.kafka.topics.lodgings-topic}")
    private String lodgingsTopic;

    private static final Logger logger = LoggerFactory.getLogger(LodgingController.class);

    private final LodgingService lodgingService;
    private final KafkaProducerService kafkaProducerService;

    public LodgingController(LodgingService lodgingService, KafkaProducerService kafkaProducerService) {
        this.lodgingService = lodgingService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/lodgings")
    public ResponseEntity<String> getLodgings() {
        List<RvpApilodging> rvpApilodgings = lodgingService.getAllLodgings();
        sendToKafka(rvpApilodgings);
        return new ResponseEntity<>("Returned " + rvpApilodgings.size() + " Reviewpro lodgings.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApilodging> lodgings) {
        for (RvpApilodging lodging : lodgings) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(lodging);
                kafkaProducerService.sendToKafka(json, lodging.getId(), lodgingsTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
