package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingCqiService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
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
public class LodgingCqiController {

    @Value("${spring.kafka.topics.lodging-cqis-topic}")
    private String lodgingCqisTopic;

    private final LodgingService lodgingService;
    private final LodgingCqiService lodgingCqiService;
    private final KafkaProducerService kafkaProducerService;

    public LodgingCqiController(
        LodgingService lodgingService,
        LodgingCqiService lodgingCqiService,
        KafkaProducerService kafkaProducerService
    ) {
        this.lodgingService = lodgingService;
        this.lodgingCqiService = lodgingCqiService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/lodging-cqi-by-pid")
    public List<RvpApiLodgingCqi> getLodgingCqis(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        return lodgingCqiService.getLodgingIndex(pid, fd, td);
    }

    @GetMapping("/lodging-cqis")
    public ResponseEntity<String> getAllLodgingCqis(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingCqi> allLodgingCqis = new ArrayList<>();
        List<String> lodgingIds = lodgingService.getLodgingIds();
        for (String lodgingId : lodgingIds) {
            allLodgingCqis.addAll(lodgingCqiService.getLodgingIndex(Integer.valueOf(lodgingId), fd, td));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sendToKafka(allLodgingCqis);
        return new ResponseEntity<>("Returned " + allLodgingCqis.size() + " Reviewpro lodging Cqis.", HttpStatus.OK);
    }

    private void sendToKafka(List<RvpApiLodgingCqi> allLodgingCqis) {
        for (RvpApiLodgingCqi lodgingCqi : allLodgingCqis) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            ObjectWriter ow = objectMapper.findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(lodgingCqi);
                kafkaProducerService.sendToKafka(json, lodgingCqi.getId().toString(), lodgingCqisTopic);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
