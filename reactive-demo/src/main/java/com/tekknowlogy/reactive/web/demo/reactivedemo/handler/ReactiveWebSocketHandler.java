package com.tekknowlogy.reactive.web.demo.reactivedemo.handler;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.tekknowlogy.reactive.web.demo.reactivedemo.beans.Reactive;
import com.tekknowlogy.reactive.web.demo.reactivedemo.repositories.ReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {
     
	 private ReactiveRepository reactiveRepository = null;

	 ReactiveWebSocketHandler(ReactiveRepository reactiveRepository) {
	        this.reactiveRepository = reactiveRepository;
	 }
	 
	 private Flux<String> eventFlux = Flux.generate(sink -> {
		    Flux<Reactive> event = reactiveRepository.findAll();
		    sink.next(event.toString());
		});
	 
	 private Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(1000L))
		        .zipWith(eventFlux, (time, event) -> event);

 
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(intervalFlux
          .map(webSocketSession::textMessage))
          .and(webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .log());
    }
}