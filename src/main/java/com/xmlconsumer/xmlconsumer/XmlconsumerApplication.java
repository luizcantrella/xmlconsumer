package com.xmlconsumer.xmlconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.reactive.function.client.WebClient;

@EnableKafka
@SpringBootApplication
public class XmlconsumerApplication {

	@Value("${xml-server-url}")
	private String xmlServerUrl;

	@Bean
	public WebClient webClientXML(WebClient.Builder builder) {
		return builder
				.baseUrl("http://restapi.adequateshop.com")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_ATOM_XML_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(XmlconsumerApplication.class, args);
	}

}
