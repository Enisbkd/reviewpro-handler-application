package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApiGlobalReviewTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApiGlobalReview getRvpApiGlobalReviewSample1() {
        return new RvpApiGlobalReview().id(1).lodgingid(1).distribution("distribution1");
    }

    public static RvpApiGlobalReview getRvpApiGlobalReviewSample2() {
        return new RvpApiGlobalReview().id(2).lodgingid(2).distribution("distribution2");
    }

    public static RvpApiGlobalReview getRvpApiGlobalReviewRandomSampleGenerator() {
        return new RvpApiGlobalReview()
            .id(intCount.incrementAndGet())
            .lodgingid(intCount.incrementAndGet())
            .distribution(UUID.randomUUID().toString());
    }
}
