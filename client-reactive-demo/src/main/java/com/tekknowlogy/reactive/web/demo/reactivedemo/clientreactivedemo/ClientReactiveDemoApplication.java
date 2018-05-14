package com.tekknowlogy.reactive.web.demo.reactivedemo.clientreactivedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication
public class ClientReactiveDemoApplication {
	
		@Bean
		ReactorNettyWebSocketClient client(){
			return new ReactorNettyWebSocketClient();
		}

	 	@Bean
	    WebClient client(@Value("${libary-service-url:http://localhost:8080/}") String url) {
	        ExchangeFilterFunction basicAuth = ExchangeFilterFunctions
	                .basicAuthentication("amine", "pw");
	        return WebClient.builder().baseUrl(url).filter(basicAuth).build();
	    }
	 	
	 	

	    @Bean
	    ApplicationRunner run(WebClient client) throws URISyntaxException {
	    	
	    	WebSocketClient clientWS = client();

	    	URI url = new URI("ws://localhost:8080/reactive");
	    	clientWS.execute(url, session ->
	    			session.receive()
	    					.doOnNext(System.out::println)
	    					.then());
	        //@formatter:off
	        return args ->
	            client
	                .get()
	                .uri("/reactive")
	                .retrieve()
	                .bodyToFlux(Reactive.class)
	                .subscribe(System.out::println);
	        //@formatter:on
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(ClientReactiveDemoApplication.class, args);
	    }
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonAutoDetect
	class Reactive {
		@JsonProperty
	    private String id;
		@JsonProperty
	    private String title;
		@JsonProperty
	    private String mode;
	}