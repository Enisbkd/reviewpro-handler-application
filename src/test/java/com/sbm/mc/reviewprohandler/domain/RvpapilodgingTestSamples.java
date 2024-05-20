package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpapilodgingTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Rvpapilodging getRvpapilodgingSample1() {
        return new Rvpapilodging().id("id1").ida(1).name("name1");
    }

    public static Rvpapilodging getRvpapilodgingSample2() {
        return new Rvpapilodging().id("id2").ida(2).name("name2");
    }

    public static Rvpapilodging getRvpapilodgingRandomSampleGenerator() {
        return new Rvpapilodging().id(UUID.randomUUID().toString()).ida(intCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
