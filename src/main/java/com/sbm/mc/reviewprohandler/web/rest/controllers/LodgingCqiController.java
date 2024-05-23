package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import com.sbm.mc.reviewprohandler.service.LodgingCqiService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingCqiController {

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

    @GetMapping("/lodgingCqi")
    public List<RvpApiLodgingCqi> getLodgingIndex(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        return lodgingCqiService.getLodgingIndex(pid, fd, td);
    }

    @GetMapping("/getAllLodgingCqis")
    public List<RvpApiLodgingCqi> getAllLodgingCqis(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingCqi> allLodgingCqis = new ArrayList<>();
        List<String> lodgingIds = lodgingService.getLodgingIds();
        for (String lodgingId : lodgingIds) {
            allLodgingCqis.addAll(lodgingCqiService.getLodgingIndex(Integer.valueOf(lodgingId), fd, td));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sendToKafka(allLodgingCqis);
        return allLodgingCqis;
    }

    private void sendToKafka(List<RvpApiLodgingCqi> allLodgingCqis) {
        for (RvpApiLodgingCqi lodgingCqi : allLodgingCqis) {
            ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(lodgingCqi);
                kafkaProducerService.sendToKafka(json, lodgingCqi.getId().toString(), "data-reviewpro-lodgingCqis");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
