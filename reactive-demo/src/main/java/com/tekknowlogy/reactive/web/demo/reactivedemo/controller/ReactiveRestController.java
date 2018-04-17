package com.tekknowlogy.reactive.web.demo.reactivedemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tekknowlogy.reactive.web.demo.reactivedemo.beans.Reactive;
import com.tekknowlogy.reactive.web.demo.reactivedemo.repositories.ReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
class ReactiveRestController {

    private final ReactiveRepository reactiveRepository;

    ReactiveRestController(ReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @GetMapping("/reactive")
    Flux<Reactive> getAllData() {
        return this.reactiveRepository.findAll();
    }

    @GetMapping("/reactive/{mode}")
    Flux<Reactive> getDataByMode(@PathVariable String mode) {
        return this.reactiveRepository.findByMode(mode);
    }
    
    @GetMapping("/reactive/create/{mode}/{title}")
    Mono<Reactive> createData(@PathVariable String mode, @PathVariable String title) {
        return this.reactiveRepository.insert(new Reactive(null, title, mode));
    }
}