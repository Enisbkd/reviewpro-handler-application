package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingController {

    private static final Logger logger = LoggerFactory.getLogger(LodgingController.class);

    private final LodgingService lodgingService;
    private final KafkaProducerService kafkaProducerService;

    public LodgingController(LodgingService lodgingService, KafkaProducerService kafkaProducerService) {
        this.lodgingService = lodgingService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/lodgings")
    public List<RvpApilodging> getLodgings() {
        List<RvpApilodging> rvpApilodgings = lodgingService.getAllLodgings();
        sendToKafka(rvpApilodgings);
        return rvpApilodgings;
    }

    private void sendToKafka(List<RvpApilodging> lodgings) {
        for (RvpApilodging lodging : lodgings) {
            ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(lodging);
                kafkaProducerService.sendToKafka(json, lodging.getId(), "data-reviewpro-lodgings");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
