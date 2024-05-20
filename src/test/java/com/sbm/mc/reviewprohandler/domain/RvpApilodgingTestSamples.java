package com.sbm.mc.reviewprohandler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RvpApilodgingTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RvpApilodging getRvpApilodgingSample1() {
        return new RvpApilodging().id("id1").ida(1).name("name1");
    }

    public static RvpApilodging getRvpApilodgingSample2() {
        return new RvpApilodging().id("id2").ida(2).name("name2");
    }

    public static RvpApilodging getRvpApilodgingRandomSampleGenerator() {
        return new RvpApilodging().id(UUID.randomUUID().toString()).ida(intCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
