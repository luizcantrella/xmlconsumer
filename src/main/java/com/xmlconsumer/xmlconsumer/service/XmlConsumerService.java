package com.xmlconsumer.xmlconsumer.service;

import com.xmlconsumer.xmlconsumer.model.XmlTraveler;
import com.xmlconsumer.xmlconsumer.repository.XmlTravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

@Service
public class XmlConsumerService {

  @Autowired
  private XmlTravelerRepository repository;
  @Autowired
  private WebClient webClient;

  public Long obterXml() {
    Mono<String> xmlString = this.webClient
        .method(HttpMethod.GET)
        .uri("/api/Traveler/1412")
        .retrieve()
        .bodyToMono(String.class);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    //Document document;
    String xmlResponse = xmlString.block();
    try {
      builder = factory.newDocumentBuilder();
      if(xmlResponse == null) {
        //TODO: logging don't found xml file
        return -1L;
      }
      StringReader stringReader = new StringReader(xmlResponse);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      //TODO: logging string parsed to document
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(document);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      StreamResult result = new StreamResult(bos);
      transformer.transform(source, result);
      byte []byteArray = bos.toByteArray();
      String travelerId = document.getDocumentElement().getFirstChild().getFirstChild().getNodeValue();
      XmlTraveler xmlTraveler = new XmlTraveler(Long.parseLong(travelerId), byteArray);
      repository.save(xmlTraveler);
      //TODO: logging persistence
      return Long.parseLong(travelerId);
    } catch (Exception e) {
      e.printStackTrace();
      return -1L;
    }
  }

}
