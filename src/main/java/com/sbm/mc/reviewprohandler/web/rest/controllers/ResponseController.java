package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.service.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResponseController {

    private final ResponseService surveyService;

    public ResponseController(ResponseService responseService) {
        this.surveyService = responseService;
    }

    @GetMapping("/responses")
    public String getSurveyResponses(
        @RequestParam String surveyId,
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        return surveyService.getSurveyResponses(surveyId, pid, fd, td, flagged, onlyPublished, dateField);
    }
}
