package me.namila.rx.reactivespringboot.core.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public class GenericRepositoryImpl<T,E extends Serializable> extends SimpleReactiveMongoRepository<T, E> implements GenericRepository<T,E> {

    private final ReactiveMongoTemplate reactiveMongoTemplate;


    public GenericRepositoryImpl(MappingMongoEntityInformation<T,E> mongoEntityInformation,
                                 ReactiveMongoTemplate reactiveMongoTemplate) {
        super(mongoEntityInformation,reactiveMongoTemplate);
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Boolean> existsByName(String name, Class<T> classType) {
        Query query = new Query(Criteria.where("name").is(name));

        return Mono.create(s -> {
            reactiveMongoTemplate.exists(query, classType).subscribe(v -> {
                if (Boolean.TRUE.equals(v)) {
                    s.success(Boolean.TRUE);
                } else {
                    s.success(Boolean.FALSE);
                }

            });
        });

    }

    @Override
    public Mono<T> findByName(String name, Class<T> classType) {
        Query query = new Query(Criteria.where("name").is(name));
        return reactiveMongoTemplate.findOne(query, classType);
    }
}
