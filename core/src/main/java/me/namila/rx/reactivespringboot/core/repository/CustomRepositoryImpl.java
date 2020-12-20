package me.namila.rx.reactivespringboot.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * The type Custom repository.
 *
 * @param <T> the type parameter
 */
public class CustomRepositoryImpl<T> implements CustomRepository<T> {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    /**
     * Instantiates a new Custom repository.
     *
     * @param reactiveMongoTemplate the reactive mongo template
     */
    @Autowired
    public CustomRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
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
