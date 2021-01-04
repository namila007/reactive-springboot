package me.namila.rx.reactivespringboot.core.repository;

import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Mono;

/**
 * The interface Custom repository.
 *
 * @param <T> the type parameter
 */
@NoRepositoryBean
public interface CustomRepository<T> {

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
