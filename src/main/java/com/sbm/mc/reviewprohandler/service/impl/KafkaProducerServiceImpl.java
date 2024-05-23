package com.sbm.mc.reviewprohandler.service.impl;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbm.mc.reviewprohandler.service.KafkaProducerService;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final Logger log = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    @Autowired(required = true)
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendToKafka(String message, String id, String topic) {
        // Check connection status
        Map<MetricName, ? extends Metric> metrics = kafkaTemplate.metrics();
        if (metrics.isEmpty()) {
            log.debug("Producer is not connected to any broker.");
        } else {
            log.debug("Producer is connected to at least one broker.");
        }

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, id, message);

        // Add callback for handling success and failure
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.debug(
                    "Message sent successfully! Topic: " +
                    result.getRecordMetadata().topic() +
                    " Partition: " +
                    result.getRecordMetadata().partition() +
                    " Offset: " +
                    result.getRecordMetadata().offset()
                );
            } else {
                log.debug("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
