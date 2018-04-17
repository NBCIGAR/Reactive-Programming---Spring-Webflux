package com.tekknowlogy.reactive.web.demo.reactivedemo.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tekknowlogy.reactive.web.demo.reactivedemo.beans.Reactive;
import com.tekknowlogy.reactive.web.demo.reactivedemo.repositories.ReactiveRepository;

import reactor.core.publisher.Flux;

@Slf4j
@Component
class ReactiveDataInitializer implements ApplicationRunner {

    private final ReactiveRepository reactiveRepository;

    ReactiveDataInitializer(ReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.reactiveRepository
            .deleteAll()
            .thenMany(
              Flux.just("Professional Java Development with the Spring Framework|rjohnson",
                "Cloud Native Java|jlong", "Spring Security 3.1|rwinch", "Spring in Action|cwalls"))
            .map(t -> t.split("\\|"))
            .map(tuple -> new Reactive(null, tuple[0], tuple[1]))
            .flatMap(this.reactiveRepository::save)
            .thenMany(this.reactiveRepository.findAll())
            .subscribe(reactive -> System.out.println(reactive.toString()));
    }
}