package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApiLodgingCqiTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApiLodgingCqi getRvpApiLodgingCqiSample1() {
        return new RvpApiLodgingCqi()
            .id(1)
            .lodgingId(1)
            .averageCurrentPeriod("averageCurrentPeriod1")
            .tendancy("tendancy1")
            .change("change1")
            .name("name1")
            .averagePreviousPeriod("averagePreviousPeriod1");
    }

    public static RvpApiLodgingCqi getRvpApiLodgingCqiSample2() {
        return new RvpApiLodgingCqi()
            .id(2)
            .lodgingId(2)
            .averageCurrentPeriod("averageCurrentPeriod2")
            .tendancy("tendancy2")
            .change("change2")
            .name("name2")
            .averagePreviousPeriod("averagePreviousPeriod2");
    }

    public static RvpApiLodgingCqi getRvpApiLodgingCqiRandomSampleGenerator() {
        return new RvpApiLodgingCqi()
            .id(intCount.incrementAndGet())
            .lodgingId(intCount.incrementAndGet())
            .averageCurrentPeriod(UUID.randomUUID().toString())
            .tendancy(UUID.randomUUID().toString())
            .change(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .averagePreviousPeriod(UUID.randomUUID().toString());
    }
}
