package me.namila.rx.reactivespringboot.core.service;

import me.namila.rx.reactivespringboot.core.constant.JSON;
import me.namila.rx.reactivespringboot.core.exception.CoreException;
import me.namila.rx.reactivespringboot.core.exception.MongoException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.model.EmployeeModel;
import me.namila.rx.reactivespringboot.core.repository.EmployeeRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/** The type Employee service. */
@Service
public class EmployeeService extends BaseService<EmployeeModel, String> {
  private static Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
  private DepartmentService departmentService;

  private EmployeeRepository employeeRepository;

  /** Instantiates a new EmployeeService. */
  public EmployeeService() {
    super(EmployeeModel.class, String.class);
  }

  /**
   * Create employee mono.
   *
   * @param employeeModel the employee model
   * @return the mono
   */
  public Mono<EmployeeModel> createEmployee(EmployeeModel employeeModel) {
    if (employeeModel.getDepartment() != null) {
      return departmentService
          .findByDepartmentNo(employeeModel.getDepartment().getDepartmentNo())
          .doOnError(
              NullPointerException.class,
              x -> {
                LOGGER.error("createEmployee {}", x.getMessage());
                throw new CoreException(
                    HttpStatus.NO_CONTENT,
                    "Department is not available on the EmployeeModel object");
              })
          .flatMap(
              department -> {
                if (department.getId() != null) {
                  employeeModel.setDepartment(new DepartmentModel(department.getDepartmentNo()));
                  employeeModel.setDepartmentNo(department.getDepartmentNo());
                  return this.create(employeeModel)
                      .map(
                          emp -> {
                            emp.setDepartment(department);
                            return emp;
                          });
                }
                if (employeeModel.getDepartment().getName() == null)
                  return Mono.error(
                      new CoreException(
                          HttpStatus.NO_CONTENT, JSON.DepartmentJSON.NAME + " is missing"));
                return this.departmentService
                    .create(employeeModel.getDepartment())
                    .flatMap(
                        dep -> {
                          employeeModel.setDepartmentNo(dep.getDepartmentNo());
                          return create(employeeModel)
                              .map(
                                  emp -> {
                                    emp.setDepartment(dep);
                                    return emp;
                                  });
                        });
              })
          .onErrorMap(
              error ->
                  new CoreException(
                      HttpStatus.INTERNAL_SERVER_ERROR,
                      "Error occurred while saving a new EmployeeModel",
                      error));
    }
    return Mono.error(
        new CoreException(HttpStatus.NO_CONTENT, "Department Object is not available"));
  }

  /**
   * Gets employee.
   *
   * @param id the id
   * @return the employee
   */
  public Mono<EmployeeModel> getEmployee(String id) {
    return get(id)
        .switchIfEmpty(
            Mono.error(
                new CoreException(HttpStatus.NO_CONTENT, "No Employee founded for id " + id)))
        .flatMap(
            emp ->
                Mono.just(emp)
                    .zipWith(
                        departmentService.findByDepartmentNo(emp.getDepartmentNo()),
                        (e, d) -> {
                          e.setDepartment(d);
                          return e;
                        }))
        .onErrorMap(
            e ->
                new CoreException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred getEmployee", e));
  }

  /**
   * Gets all employee.
   *
   * @param pageable the pageable
   * @return the all employee
   */
  public Mono<Page<EmployeeModel>> getAllEmployee(Pageable pageable) {
    return getAllByQuery(pageable)
        .flatMap(
            page ->
                Flux.fromIterable(page.getContent())
                    .parallel()
                    .runOn(Schedulers.parallel())
                    .doOnNext(emp -> LOGGER.info("emp: {}", emp))
                    .flatMap(
                        emp ->
                            departmentService
                                .findByDepartmentNo(emp.getDepartmentNo())
                                .doOnNext(dep -> LOGGER.info("emp: {}, founded dep: {}", emp, dep))
                                .doOnError(
                                    ex ->
                                        LOGGER.error(
                                            "getAllEmployee dept find {}", ex.getMessage()))
                                .map(
                                    dep -> {
                                      emp.setDepartment(dep);
                                      return (emp);
                                    }))
                    .sequential()
                    .onErrorMap(
                        e ->
                            new CoreException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Error Occurred while getting all employees",
                                e))
                    .collectList()
                    .map(
                        list ->
                            PageableExecutionUtils.getPage(
                                list, pageable, page::getTotalElements)));
  }

  /**
   * Delete employee mono.
   *
   * @param id the id
   * @return the mono
   */
  public Mono<JSONObject> deleteEmployee(String id) {
    return delete(id)
        .map(
            b -> {
              if (Boolean.FALSE.equals(b))
                throw new MongoException("No Employee found for the given id: " + id);
              JSONObject jsonObject = new JSONObject();
              jsonObject.appendField("deleted", "ok");
              return jsonObject;
            })
        .onErrorMap(
            error -> {
              LOGGER.error("Error Occured: {}", error.getMessage());
              return new CoreException(HttpStatus.BAD_REQUEST, "Employee Deletion Error", error);
            });
  }

  /**
   * Sets employee repository.
   *
   * @param employeeRepository the employee repository
   */
  @Autowired
  public void setEmployeeRepository(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
    //    this.setGenericRepository(employeeRepository);
  }

  /**
   * Sets department service.
   *
   * @param departmentService the department service
   */
  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }
}
