package me.namila.rx.reactivespringboot.core.service;

import me.namila.rx.reactivespringboot.core.constant.JSON;
import me.namila.rx.reactivespringboot.core.exception.CoreException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.model.EmployeeModel;
import me.namila.rx.reactivespringboot.core.repository.EmployeeRepository;
import me.namila.rx.reactivespringboot.core.repository.GenericRepository;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
  @Mock private EmployeeRepository employeeRepository;

  @Mock private DepartmentService departmentService;

  @Mock private GenericRepository<EmployeeModel, String> genericRepository;

  @Mock private ReactiveMongoTemplate reactiveMongoTemplate;

  @InjectMocks private EmployeeService employeeService;

  private EmployeeModel employeeModel;

  private DepartmentModel departmentModel;

  @BeforeEach
  public void setUp() {
    departmentModel =
        new DepartmentModel(
            UUID.randomUUID().toString(), 1, "test", DateUtil.now(), DateUtil.now());
    employeeModel =
        new EmployeeModel(
            UUID.randomUUID().toString(),
            "test",
            "test@gmail.com",
            null,
            null,
            DateUtil.now(),
            DateUtil.now());
  }

  @Test
  void createEmployeeTestWithNewDepartment() {
    DepartmentModel departmentModel1 =
        new DepartmentModel(null, 1, "test", DateUtil.now(), DateUtil.now());
    employeeModel.setDepartment(departmentModel1);
    Mockito.when(departmentService.findByDepartmentNo(Mockito.any(Integer.class)))
        .thenReturn(Mono.just(departmentModel1));
    Mockito.when(departmentService.createDepartment(Mockito.any()))
        .thenReturn(Mono.just(departmentModel));
    Mockito.when(genericRepository.save(Mockito.any())).thenReturn(Mono.just(employeeModel));
    StepVerifier.create(employeeService.createEmployee(employeeModel))
        .expectNext(employeeModel)
        .verifyComplete();
  }

  @Test
  void createEmployeeTestWithExistingDepartment() {
    employeeModel.setDepartment(new DepartmentModel(departmentModel.getDepartmentNo()));
    Mockito.when(departmentService.findByDepartmentNo(Mockito.any(Integer.class)))
        .thenReturn(Mono.just(departmentModel));
    Mockito.when(genericRepository.save(Mockito.any())).thenReturn(Mono.just(employeeModel));
    StepVerifier.create(employeeService.createEmployee(employeeModel))
        .expectNext(employeeModel)
        .verifyComplete();
  }

  @Test
  void createEmployeeTestNameMissingException() {
    departmentModel.setName(null);
    departmentModel.setId("");
    employeeModel.setDepartment(departmentModel);
    Mockito.when(departmentService.findByDepartmentNo(Mockito.any(Integer.class)))
        .thenReturn(Mono.just(new DepartmentModel()));
    Mockito.when(genericRepository.save(Mockito.any())).thenReturn(Mono.just(employeeModel));
    StepVerifier.create(employeeService.createEmployee(employeeModel))
        .consumeErrorWith(
            ex -> {
              Assertions.assertEquals(CoreException.class, ex.getClass());
              Assertions.assertEquals(
                  JSON.DepartmentJSON.NAME + " is missing", ex.getCause().getMessage());
              Assertions.assertEquals(
                  HttpStatus.NO_CONTENT, ((CoreException) ex.getCause()).getStatus());
            })
        .log()
        .verify();
  }

  @Test
  void getEmployeeTest() {
    employeeModel.setDepartmentNo(departmentModel.getDepartmentNo());
    Mockito.when(genericRepository.findById(Mockito.any(String.class)))
        .thenReturn(Mono.just(employeeModel));
    Mockito.when(departmentService.findByDepartmentNo(Mockito.any(Integer.class)))
        .thenReturn(Mono.just(departmentModel));
    StepVerifier.create(employeeService.getEmployee("TEST"))
        .thenConsumeWhile(
            emp -> {
              Assertions.assertEquals(emp.getUsername(), employeeModel.getUsername());
              Assertions.assertEquals(emp.getDepartment(), departmentModel);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void getEmployeeTestNoEmployeeFoundException() {
    Mockito.when(genericRepository.findById(Mockito.any(String.class))).thenReturn(Mono.empty());
    StepVerifier.create(employeeService.getEmployee(employeeModel.getId()))
        .consumeErrorWith(
            ex -> {
              Assertions.assertEquals(CoreException.class, ex.getClass());
              Assertions.assertEquals(
                  "No Employee founded for id " + employeeModel.getId(),
                  ((CoreException) ex).getReason());
            })
        .verify();
  }
}
