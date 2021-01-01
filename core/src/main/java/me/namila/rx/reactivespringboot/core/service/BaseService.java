package me.namila.rx.reactivespringboot.core.service;

import lombok.NoArgsConstructor;
import me.namila.rx.reactivespringboot.core.model.PageableResponse;
import me.namila.rx.reactivespringboot.core.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * The type Base service.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@NoArgsConstructor
public abstract class BaseService<T, ID> {

  private GenericRepository<T, ID> genericRepository;

  private Class<T> classType;
  private Class<ID> idType;
  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;

  /**
   * Instantiates a new Base service.
   *
   * @param classType the class type
   * @param idType the id type
   */
  public BaseService(Class<T> classType, Class<ID> idType) {
    this.classType = classType;
    this.idType = idType;
  }

  /**
   * Create mono.
   *
   * @param item the item
   * @return the mono
   */
  protected Mono<T> create(T item) {
    return genericRepository.save(item);
  }

  /**
   * Get mono.
   *
   * @param id the id
   * @return the mono
   */
  protected Mono<T> get(ID id) {
    return genericRepository.findById(id);
  }

  /**
   * Delete mono. check if id exists, if yes delete and return true, else false
   *
   * @param id the id
   * @return the mono
   */
  protected Mono<Boolean> delete(ID id) {
    return genericRepository
            .existsById(id)
            .flatMap(
                    v -> {
                      if (Boolean.TRUE.equals(v))
                        return genericRepository.deleteById(id).thenReturn(Boolean.TRUE);
                      else return Mono.just(Boolean.FALSE);
                    });
  }

  /**
   * Gets all. better to do this on DB query (performance hit) check @getAllByQuery func
   *
   * @param pageable the pageable
   * @return the all
   */
  protected Flux<T> getAll(Pageable pageable) {
    return genericRepository
            .findAll(pageable.getSort())
            .skip(pageable.getOffset())
            .take(pageable.getPageSize());
  }

  /**
   * Gets all by query.
   *
   * @param pageable the pageable
   * @return the all by query
   */
  protected Mono<Page<T>> getAllByQuery(Pageable pageable) {
    Query query = new Query().with(pageable);

    return reactiveMongoTemplate
            .find(query, classType)
            .collectList()
            .flatMap(
                    list ->
                            reactiveMongoTemplate
                                    .count(new Query(), this.classType)
                                    .subscribeOn(Schedulers.immediate())
                                    .map(
                                            count ->
                                                    new PageableResponse<T>(
                                                            list,
                                                            pageable.getPageNumber(),
                                                            pageable.getPageSize(),
                                                            pageable.getSort(),
                                                            count)));
  }

  /**
   * Sets generic repository.
   *
   * @param genericRepository the generic repository
   */
  protected void setGenericRepository(GenericRepository<T, ID> genericRepository) {
    this.genericRepository = genericRepository;
  }
}
