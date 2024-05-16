package com.sbm.mc.reviewprohandler;

import com.sbm.mc.reviewprohandler.config.AsyncSyncConfiguration;
import com.sbm.mc.reviewprohandler.config.EmbeddedMongo;
import com.sbm.mc.reviewprohandler.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ReviewproHandlerApplicationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedMongo
public @interface IntegrationTest {
}
