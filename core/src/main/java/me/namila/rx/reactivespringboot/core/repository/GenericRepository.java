package me.namila.rx.reactivespringboot.core.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The interface Generic repository.
 *
 * @param <T> the Model type parameter
 * @param <E> the ID type parameter
 */
@NoRepositoryBean
public interface GenericRepository<T, E> extends ReactiveMongoRepository<T, E>, CustomRepository<T> {
}
