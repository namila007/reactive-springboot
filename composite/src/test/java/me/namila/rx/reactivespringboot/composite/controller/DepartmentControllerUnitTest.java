package me.namila.rx.reactivespringboot.composite.controller;

import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The type Department controller unit test.
 */
@ExtendWith(SpringExtension.class)
public class DepartmentControllerUnitTest {

  @Mock
  private DepartmentService departmentService;

  private DepartmentModel departmentData;

  @InjectMocks
  private DepartmentController departmentController;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    departmentData = new DepartmentModel();
    departmentData.setName("test");
    departmentData.setDepartmentNo(1);
  }

  /**
   * Create department test.
   */
  @Test
  public void createDepartmentTest() {
    ResponseEntity<DepartmentModel> testResponse =
            new ResponseEntity<>(departmentData, HttpStatus.CREATED);

    Mockito.when(departmentService.createDepartment(Mockito.any()))
            .thenReturn(Mono.just(departmentData));
    Mono<ResponseEntity<DepartmentModel>> response =
            departmentController.createDepartment(departmentData);
    StepVerifier.create(response).expectNext(testResponse).expectComplete();
  }

  /**
   * Create department test exception.
   */
  @Test
  public void createDepartmentTestException() {
    Mockito.when(departmentService.createDepartment(Mockito.any()))
            .thenReturn(Mono.error(new Exception("Test")));
    Mono<ResponseEntity<DepartmentModel>> response =
            departmentController.createDepartment(departmentData);
    StepVerifier.create(response).expectError(Exception.class).verify();
  }
}
