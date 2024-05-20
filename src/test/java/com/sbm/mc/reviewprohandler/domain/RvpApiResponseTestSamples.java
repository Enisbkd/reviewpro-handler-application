package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApiResponseTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApiResponse getRvpApiResponseSample1() {
        return new RvpApiResponse().id(1).surveyId(1).lodgingId(1);
    }

    public static RvpApiResponse getRvpApiResponseSample2() {
        return new RvpApiResponse().id(2).surveyId(2).lodgingId(2);
    }

    public static RvpApiResponse getRvpApiResponseRandomSampleGenerator() {
        return new RvpApiResponse()
            .id(intCount.incrementAndGet())
            .surveyId(intCount.incrementAndGet())
            .lodgingId(intCount.incrementAndGet());
    }
}
