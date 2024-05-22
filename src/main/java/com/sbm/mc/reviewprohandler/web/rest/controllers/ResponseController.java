package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import com.sbm.mc.reviewprohandler.service.ResponseService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
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
public class ResponseController {

    private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

    private final SurveyService surveyService;
    private final LodgingService lodgingService;
    private final ResponseService responseService;
    private final LodgingScoreService lodgingScoreServiceA;

    public ResponseController(
        SurveyService surveyService,
        LodgingService lodgingService,
        ResponseService responseService1,
        LodgingScoreService lodgingScoreServiceA
    ) {
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
        this.responseService = responseService1;
        this.lodgingScoreServiceA = lodgingScoreServiceA;
    }

    @GetMapping("/responses")
    public List<RvpApiResponse> getSurveyResponses(
        @RequestParam String surveyId,
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateField
    ) {
        return responseService.getSurveyResponses(surveyId, pid, fd, td, flagged, onlyPublished, dateField);
    }

    @GetMapping("/getResponsesByPid")
    public List<String> getResponsesByPid(
        @RequestParam int pid,
        @RequestParam String fd,
        @RequestParam String td,
        @RequestParam String flagged,
        @RequestParam boolean onlyPublished,
        @RequestParam String dateFieldString
    ) {
        List<String> globalReviewsByPid = new ArrayList<>();
        List<String> surveyIds = surveyService.extractSurveyIds();
        String response = null;
        for (String surveyId : surveyIds) {
            logger.info("**********************************************************************");
            List<RvpApiResponse> responsesByPid = getSurveyResponses(surveyId, pid, fd, td, flagged, onlyPublished, dateFieldString);
            logger.info("Response for pid : " + pid + " on survey: " + surveyId + " :: " + response);
            globalReviewsByPid.add(response);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return globalReviewsByPid;
    }

    @GetMapping("/getAllResponses")
    public List<List<String>> getAllResponses(@RequestParam String fd, @RequestParam String td) {
        List<List<String>> responses = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            responses.add(getResponsesByPid(Integer.valueOf(logingId), fd, td, null, false, null));
        }
        return responses;
    }
}
