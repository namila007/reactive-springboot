package me.namila.rx.reactivespringboot.composite.controller;

import me.namila.rx.reactivespringboot.core.constant.Routes;
import me.namila.rx.reactivespringboot.core.model.EmployeeModel;
import me.namila.rx.reactivespringboot.core.service.EmployeeService;
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

/** The type Employee controller. */
@RestController
@RequestMapping(
    value = Routes.BASE_END_POINT + Routes.EMPLOYEE_BASEURL,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

  private EmployeeService employeeService;

  /**
   * Create employee mono.
   *
   * @param employeeModel the employee model
   * @return the mono
   */
  @PostMapping
  public Mono<ResponseEntity<EmployeeModel>> createEmployee(
      @NotNull @RequestBody EmployeeModel employeeModel) {
    return employeeService
        .createEmployee(employeeModel)
        .subscribeOn(getScheduler(Schedulers.boundedElastic()))
        .map(x -> new ResponseEntity<>(x, HttpStatus.CREATED));
  }

  /**
   * Gets employee.
   *
   * @param id the id
   * @return the employee
   */
  @GetMapping(path = Routes.ID_PARAM)
  public Mono<ResponseEntity<EmployeeModel>> getEmployee(@PathVariable String id) {
    return employeeService
        .getEmployee(id)
        .subscribeOn(getScheduler(Schedulers.boundedElastic()))
        .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  /**
   * Gets all employee.
   *
   * @param pageable the pageable
   * @return the all employee
   */
  @GetMapping
  public Mono<ResponseEntity<Page<EmployeeModel>>> getAllEmployee(Pageable pageable) {
    return employeeService
        .getAllEmployee(pageable)
        .subscribeOn(getScheduler(Schedulers.boundedElastic()))
        .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  /**
   * Delete employee mono.
   *
   * @param id the id
   * @return the mono
   */
  @DeleteMapping(path = Routes.ID_PARAM)
  public Mono<ResponseEntity<Object>> deleteEmployee(@PathVariable String id) {
    return employeeService
        .deleteEmployee(id)
        .subscribeOn(getScheduler(Schedulers.boundedElastic()))
        .map(x -> new ResponseEntity<>(x, HttpStatus.OK));
  }

  private Scheduler getScheduler(Scheduler schedulers) {
    return schedulers;
  }

  /**
   * Sets employee service.
   *
   * @param employeeService the employee service
   */
  @Autowired
  public void setEmployeeService(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }
}
