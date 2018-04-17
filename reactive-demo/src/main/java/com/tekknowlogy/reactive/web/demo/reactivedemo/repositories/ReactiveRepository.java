package com.tekknowlogy.reactive.web.demo.reactivedemo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.tekknowlogy.reactive.web.demo.reactivedemo.beans.Reactive;

import reactor.core.publisher.Flux;

public interface ReactiveRepository extends ReactiveMongoRepository<Reactive, String>  {
	
	 Flux<Reactive> findByMode(String mode);

}
