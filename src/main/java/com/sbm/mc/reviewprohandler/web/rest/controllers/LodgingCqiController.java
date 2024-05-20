package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.service.LodgingCqiService;
import com.sbm.mc.reviewprohandler.service.LodgingScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LodgingCqiController {

    private final LodgingCqiService lodgingCqiService;

    public LodgingCqiController(LodgingCqiService lodgingCqiService) {
        this.lodgingCqiService = lodgingCqiService;
    }

    @GetMapping("/lodgingIndex")
    public String getLodgingIndex(@RequestParam String pid, @RequestParam String fd, @RequestParam String td) {
        return lodgingCqiService.getLodgingIndex(pid, fd, td);
    }
}
