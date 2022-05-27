package com.xmlconsumer.xmlconsumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

  @Value("${topic.name.producer}")
  private String topicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public ProducerService(final KafkaTemplate<String, String> _kafkaTemplate) {
    this.kafkaTemplate = _kafkaTemplate;
  }

  public void send(String message) {
    kafkaTemplate.send(topicName, message);
  }
}
