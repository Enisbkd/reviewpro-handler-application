package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScore;
import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import com.sbm.mc.reviewprohandler.service.LodgingService;
import com.sbm.mc.reviewprohandler.service.SurveyService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingScoreController {

    private final LodgingScoreService lodgingScoreService;
    private final SurveyService surveyService;
    private final LodgingService lodgingService;

    public LodgingScoreController(LodgingScoreService lodgingScoreService, SurveyService surveyService, LodgingService lodgingService) {
        this.lodgingScoreService = lodgingScoreService;
        this.surveyService = surveyService;
        this.lodgingService = lodgingService;
    }

    @GetMapping("/lodgingScore")
    public RvpApiLodgingScore getLodgingDetails(
        @RequestParam String surveyId,
        @RequestParam Integer pid,
        @RequestParam String fd,
        @RequestParam String td
    ) {
        return lodgingScoreService.getLodgingScore(surveyId, pid, fd, td);
    }

    @GetMapping("/lodgingScoresbyPid")
    public List<RvpApiLodgingScore> getLodgingScoresByPid(@RequestParam Integer pid, @RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingScore> rvpApiLodgingScoresbyPid = new ArrayList<>();
        List<String> surveyIds = surveyService.extractSurveyIds();
        for (String surveyId : surveyIds) {
            rvpApiLodgingScoresbyPid.add(lodgingScoreService.getLodgingScore(surveyId, pid, fd, td));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return rvpApiLodgingScoresbyPid;
    }

    @GetMapping("/AllLodgingScores")
    public List<RvpApiLodgingScore> getAllLodgingScores(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingScore> allLodgingScores = new ArrayList<>();

        List<String> lodgingIds = lodgingService.getLodgingIds();
        for (String lodgingId : lodgingIds) {
            allLodgingScores.addAll(getLodgingScoresByPid(Integer.valueOf(lodgingId), fd, td));
        }

        return allLodgingScores;
    }
}
