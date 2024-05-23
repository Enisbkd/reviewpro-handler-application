package com.sbm.mc.reviewprohandler.web.rest.controllers;

import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
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

    public LodgingCqiController(LodgingService lodgingService, LodgingCqiService lodgingCqiService) {
        this.lodgingService = lodgingService;
        this.lodgingCqiService = lodgingCqiService;
    }

    @GetMapping("/lodgingCqi")
    public List<RvpApiLodgingCqi> getLodgingIndex(@RequestParam String pid, @RequestParam String fd, @RequestParam String td) {
        return lodgingCqiService.getLodgingIndex(pid, fd, td);
    }

    @GetMapping("/AllLodgingCqis")
    public List<RvpApiLodgingCqi> getAllLodgingCqis(@RequestParam String fd, @RequestParam String td) {
        List<RvpApiLodgingCqi> allLodgingCqis = new ArrayList<>();
        List<String> lodgingIds = lodgingService.getLodgingIds();
        for (String lodgingId : lodgingIds) {
            allLodgingCqis.addAll(lodgingCqiService.getLodgingIndex(lodgingId, fd, td));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return allLodgingCqis;
    }
}
