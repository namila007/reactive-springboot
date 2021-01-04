package me.namila.rx.reactivespringboot.core.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The interface Generic repository.
 *
 * @param <T> the Model type parameter
 * @param <E> the ID type parameter
 */
@NoRepositoryBean
public interface GenericRepository<T, E extends Serializable> extends ReactiveMongoRepository<T, E> {
    /**
     * Exists by name single.
     *
     * @param name      the name
     * @param classType the class type
     * @return the single
     */
    Mono<Boolean> existsByName(String name, Class<T> classType);

    /**
     * Find by name single.
     *
     * @param name      the name
     * @param classType the class type
     * @return the single
     */
    Mono<T> findByName(String name, Class<T> classType);
}
