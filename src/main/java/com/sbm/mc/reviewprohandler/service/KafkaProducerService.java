package com.sbm.mc.reviewprohandler.service;

public interface KafkaProducerService {
    void sendToKafka(String json, String id, String topic);
}
