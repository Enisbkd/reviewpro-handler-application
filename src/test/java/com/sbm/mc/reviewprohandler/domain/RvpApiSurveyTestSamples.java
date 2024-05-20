package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApiSurveyTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApiSurvey getRvpApiSurveySample1() {
        return new RvpApiSurvey().id(1).languages("languages1").outOf(1).name("name1").pids("pids1");
    }

    public static RvpApiSurvey getRvpApiSurveySample2() {
        return new RvpApiSurvey().id(2).languages("languages2").outOf(2).name("name2").pids("pids2");
    }

    public static RvpApiSurvey getRvpApiSurveyRandomSampleGenerator() {
        return new RvpApiSurvey()
            .id(intCount.incrementAndGet())
            .languages(UUID.randomUUID().toString())
            .outOf(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .pids(UUID.randomUUID().toString());
    }
}
