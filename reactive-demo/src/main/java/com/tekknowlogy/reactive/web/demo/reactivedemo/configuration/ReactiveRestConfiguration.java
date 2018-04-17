package com.tekknowlogy.reactive.web.demo.reactivedemo.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import com.tekknowlogy.reactive.web.demo.reactivedemo.beans.Reactive;
import com.tekknowlogy.reactive.web.demo.reactivedemo.repositories.ReactiveRepository;

@Configuration
class ReactiveRestConfiguration {
	
	@Autowired
	private WebSocketHandler webSocketHandler;

    @Bean
    RouterFunction<?> routes(ReactiveRepository br) {
        return
            route(GET("/reactive"),
                req -> ok().body(br.findAll(), Reactive.class))
            .andRoute(GET("/reactive/{mode}"),
                req -> ok().body(br.findByMode(req.pathVariable("mode")), Reactive.class));
    }
    
    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/event-emitter", webSocketHandler);
     
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }
}