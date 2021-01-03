package me.namila.rx.reactivespringboot.core.service;

import me.namila.rx.reactivespringboot.core.exception.MongoException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.repository.DepartmentRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

/** The type Department service. */
@Service
public class DepartmentService extends BaseService<DepartmentModel, String> {

  private static Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
  private DepartmentRepository departmentRepository;

  /** Instantiates a new Department service. */
  public DepartmentService() {
    super(DepartmentModel.class, String.class);
  }

  /**
   * Create department mono.
   *
   * @param department the department
   * @return the mono
   */
  public Mono<DepartmentModel> createDepartment(@NotNull DepartmentModel department) {

    return this.create(department)
        .onErrorMap(
            x -> {
              LOGGER.error("Service Error: {}", x.getMessage());
              return new MongoException("Department Creation Error", x);
            });
  }

  /**
   * Gets department.
   *
   * @param id the id
   * @return the department
   */
  public Mono<DepartmentModel> getDepartment(String id) {
    return this.get(id);
    //        .map(x->
    // x.add(linkTo(methodOn(DepartmentModel.class).setId(x.getId()))).withSelfRel()));
  }

  /**
   * Gets all department.
   *
   * @param pageable the pageable
   * @return the all department
   */
  public Mono<Page<DepartmentModel>> getAllDepartment(Pageable pageable) {
    //    return this.getAll(pageable).collectList().map(x-> new PageImpl<>(x));
    return getAllByQuery(pageable);
  }

  /**
   * Sets department repository.
   *
   * @param departmentRepository the department repository
   */
  @Autowired
  public void setDepartmentRepository(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
    //    this.setGenericRepository(departmentRepository);
  }

  /**
   * Delete department mono.
   *
   * @param id the id
   * @return the mono
   */
  public Mono<JSONObject> deleteDepartment(String id) {
    //ToDo delete employees when deleting a dept
    return this.delete(id)
        .map(
            val -> {
              if (Boolean.FALSE.equals(val))
                throw new MongoException("No Department found for the given id: " + id);
              JSONObject jsonObject = new JSONObject();
              jsonObject.appendField("deleted", "ok");
              return jsonObject;
            })
        .onErrorMap(
            error -> {
              LOGGER.error("Error Occured: {}", error.getMessage());
              return new MongoException(HttpStatus.BAD_REQUEST, "Department Deletion Error", error);
            });
  }

  public Mono<Boolean> isExitsByDepartmentNo(Integer departmentNo) {
    return this.isExistsByUniqueId(new DepartmentModel(departmentNo))
        .switchIfEmpty(Mono.just(Boolean.FALSE));
  }

  public Mono<DepartmentModel> findByDepartmentNo(Integer departmentNo) {
    return this.findByUniqueId(new DepartmentModel(departmentNo))
        .switchIfEmpty(Mono.just(new DepartmentModel()))
        .onErrorReturn(new DepartmentModel());
  }
}
