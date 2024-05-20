package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApiLodgingScoreTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApiLodgingScore getRvpApiLodgingScoreSample1() {
        return new RvpApiLodgingScore().id(1).lodgingId(1).surveyId(1);
    }

    public static RvpApiLodgingScore getRvpApiLodgingScoreSample2() {
        return new RvpApiLodgingScore().id(2).lodgingId(2).surveyId(2);
    }

    public static RvpApiLodgingScore getRvpApiLodgingScoreRandomSampleGenerator() {
        return new RvpApiLodgingScore()
            .id(intCount.incrementAndGet())
            .lodgingId(intCount.incrementAndGet())
            .surveyId(intCount.incrementAndGet());
    }
}
