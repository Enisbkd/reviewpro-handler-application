package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingController {

    private static final Logger logger = LoggerFactory.getLogger(LodgingController.class);

    private final LodgingService lodgingService;

    public LodgingController(LodgingService lodgingService) {
        this.lodgingService = lodgingService;
    }

    @GetMapping("/lodgings")
    public List<RvpApilodging> getLodgings() {
        return lodgingService.getLodgings();
    }
}
