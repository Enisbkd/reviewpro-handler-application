package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingScoreController {

    private final LodgingScoreService lodgingScoreService;

    public LodgingScoreController(LodgingScoreService lodgingScoreService) {
        this.lodgingScoreService = lodgingScoreService;
    }

    @GetMapping("/lodgingScore")
    public String getLodgingDetails(
        @RequestParam String surveyId,
        @RequestParam Long pid,
        @RequestParam String fd,
        @RequestParam String td
    ) {
        return lodgingScoreService.getLodgingScore(surveyId, pid, fd, td);
    }
}
