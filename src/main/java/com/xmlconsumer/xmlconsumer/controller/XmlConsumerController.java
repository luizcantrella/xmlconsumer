package com.xmlconsumer.xmlconsumer.controller;

import com.xmlconsumer.xmlconsumer.service.ProducerService;
import com.xmlconsumer.xmlconsumer.service.XmlConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xml-consumer")
public class XmlConsumerController {

  @Autowired
  private XmlConsumerService xmlConsumerService;
  @Autowired
  private ProducerService producerService;

  // 1 - receive the request
  @GetMapping
  public void getXml() {
    // 2 - call service to get xml and save in postgres
    Long travelerId = xmlConsumerService.obterXml();
    // 4 - public in a Kafka topic
    producerService.send(travelerId.toString());

    // 5 - return success
  }

}
