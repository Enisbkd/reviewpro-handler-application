package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import com.sbm.mc.reviewprohandler.service.GlobalReviewService;
import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
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
public class GlobalReviewController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalReviewController.class);

    private final GlobalReviewService globalReviewService;
    private final SurveyService surveyService;
    private final LodgingService lodgingService;

    public GlobalReviewController(
        LodgingScoreService lodgingScoreService,
        GlobalReviewService globalReviewService,
        SurveyService surveyService,
        LodgingService lodgingService
    ) {
        this.globalReviewService = globalReviewService;
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
    }

    @GetMapping("/globalReview")
    public RvpApiGlobalReview getNps(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        return globalReviewService.getGlobalReview(pid, fd, td);
    }

    @GetMapping("/getAllGlobalReviewsByPid")
    public List<RvpApiGlobalReview> getAllSurveysNpsByPid(@RequestParam int pid, @RequestParam String fd, @RequestParam String td) {
        List<RvpApiGlobalReview> globalReviewsByPid = new ArrayList<>();
        List<String> surveyIds = surveyService.extractSurveyIds();
        RvpApiGlobalReview review = null;
        for (String surveyId : surveyIds) {
            logger.debug("**********************************************************************");
            review = globalReviewService.getGlobalReview(pid, fd, td);
            logger.debug("Review for pid : " + pid + " on survey: " + surveyId + " :: " + review);
            globalReviewsByPid.add(review);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return globalReviewsByPid;
    }

    @GetMapping("/getAllGlobalReviews")
    public List<List<RvpApiGlobalReview>> getAllSurveysNps(@RequestParam String fd, @RequestParam String td) {
        List<List<RvpApiGlobalReview>> globalReviews = new ArrayList<>();
        List<String> logingIds = lodgingService.getLodgingIds();
        for (String logingId : logingIds) {
            globalReviews.add(getAllSurveysNpsByPid(Math.toIntExact(Long.valueOf(logingId)), fd, td));
        }
        return globalReviews;
    }
}
