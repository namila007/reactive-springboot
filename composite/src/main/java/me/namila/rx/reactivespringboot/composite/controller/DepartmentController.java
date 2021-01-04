package me.namila.rx.reactivespringboot.composite.controller;

import me.namila.rx.reactivespringboot.core.constant.Routes;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.validation.constraints.NotNull;

/**
 * The type Department controller.
 */
@RestController
@RequestMapping(
        value = Routes.BASE_END_POINT + Routes.DEPARTMENT_BASEURL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

  private DepartmentService departmentService;

  /**
   * Create department mono.
   *
   * @param department the department
   * @return the mono
   */
  @PostMapping
  public Mono<ResponseEntity<DepartmentModel>> createDepartment(
          @NotNull @RequestBody DepartmentModel department) {
    return departmentService
            .createDepartment(department)
            .subscribeOn(getScheduler(Schedulers.boundedElastic()))
            .map(x -> new ResponseEntity<>(x, HttpStatus.CREATED));
  }

  /**
   * Gets department.
   *
   * @param id the id
   * @return the department
   */
  @GetMapping(path = Routes.ID_PARAM)
  public Mono<ResponseEntity<DepartmentModel>> getDepartment(@PathVariable String id) {
    return departmentService
            .getDepartment(id)
            .subscribeOn(getScheduler(Schedulers.boundedElastic()))
            .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  /**
   * Gets all departments.
   *
   * @param pageable the pageable
   * @return the all departments
   */
  @GetMapping
  public Mono<ResponseEntity<Page<DepartmentModel>>> getAllDepartments(Pageable pageable) {
    return departmentService
            .getAllDepartment(pageable)
            .subscribeOn(getScheduler(Schedulers.boundedElastic()))
            .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  /**
   * Delete department mono.
   *
   * @param id the id
   * @return the mono
   */
  @DeleteMapping(path = Routes.ID_PARAM)
  public Mono<ResponseEntity<Object>> deleteDepartment(@PathVariable String id) {
    return departmentService
            .deleteDepartment(id)
            .subscribeOn(getScheduler(Schedulers.boundedElastic()))
            .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  private Scheduler getScheduler(Scheduler schedulers) {
    return schedulers;
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
