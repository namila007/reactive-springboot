package me.namila.rx.reactivespringboot.composite.controller;

import me.namila.rx.reactivespringboot.core.constant.Routes;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = Routes.BASE_END_POINT + Routes.DEPARTMENT_BASEURL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

  private DepartmentService departmentService;

  public Flux<DepartmentModel> getDepartments() {
    return null;
  }

  @PostMapping
  public Mono<ResponseEntity<DepartmentModel>> createDepartment(@NotNull @RequestBody
                                                                        DepartmentModel department) {
    return departmentService.createDepartment(department).subscribeOn(getScheduler(Schedulers.boundedElastic())).map(x ->
            new ResponseEntity<>(x, HttpStatus.CREATED));
  }

  @GetMapping(path = Routes.ID_PARAM)
  public Mono<ResponseEntity<DepartmentModel>> getDepartment(@PathVariable String id) {
    return departmentService.getDepartment(id).subscribeOn(getScheduler(Schedulers.boundedElastic())).map(x ->
            new ResponseEntity<>(x, HttpStatus.OK));
  }

  @GetMapping
  public Flux<ResponseEntity<DepartmentModel>> getAllDepartments(@RequestBody Pageable pageable) {
    return departmentService.getAllDepartment().subscribeOn(getScheduler(Schedulers.boundedElastic())).map(x ->
            new ResponseEntity<>(x, HttpStatus.OK));
  }


  private Scheduler getScheduler(Scheduler schedulers) {
    return schedulers;
  }

  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }
}
