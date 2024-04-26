package com.mamani.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, S, ID> {
    Mono<S> save(T t);
    Mono<S> update(T t);
    Flux<S> readAll();
    Mono<S> readById(ID id);
    Mono<Void> deleteById(ID id);

}
