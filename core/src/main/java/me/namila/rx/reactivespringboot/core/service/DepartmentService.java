package me.namila.rx.reactivespringboot.core.service;

import me.namila.rx.reactivespringboot.core.exception.MongoException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
public class DepartmentService extends BaseService<DepartmentModel, String> {

  private static Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
  private DepartmentRepository departmentRepository;

  public DepartmentService() {
    super(DepartmentModel.class, String.class);
  }

  public Mono<DepartmentModel> createDepartment(@NotNull DepartmentModel department) {

    return this.create(department)
            .onErrorMap(x -> {
              LOGGER.error("Service Error: {}", x.getMessage());
              return new MongoException("Department Creation Error", x);
            });
  }

  public Mono<DepartmentModel> getDepartment(String id) {
    return this.get(id);
//        .map(x-> x.add(linkTo(methodOn(DepartmentModel.class).setId(x.getId()))).withSelfRel()));
  }


  public Mono<Page<DepartmentModel>> getAllDepartment(Pageable pageable) {
//    return this.getAll(pageable).collectList().map(x-> new PageImpl<>(x));
    return getAllByQuery(pageable);
  }


  @Autowired
  public void setDepartmentRepository(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
    this.setGenericRepository(departmentRepository);
  }

}
