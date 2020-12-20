package me.namila.rx.reactivespringboot.core.service;

import lombok.NoArgsConstructor;
import me.namila.rx.reactivespringboot.core.repository.GenericRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoArgsConstructor
public abstract class BaseService<T, ID> {

  private GenericRepository<T, ID> genericRepository;

  protected Mono<T> create(T item) {
    return genericRepository.save(item);
  }

  protected Mono<T> get(ID id) {
    return genericRepository.findById(id);
  }

  protected Flux<T> getAll(Pageable pageable) {
    return genericRepository.findAll(pageable.getSort()).;
  }

  protected void setGenericRepository(GenericRepository<T, ID> genericRepository) {
    this.genericRepository = genericRepository;
  }
}
