package me.namila.rx.reactivespringboot.core.service;

import me.namila.rx.reactivespringboot.core.exception.MongoException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.repository.DepartmentRepository;
import me.namila.rx.reactivespringboot.core.repository.GenericRepository;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {
  @Mock private DepartmentRepository departmentRepository;

  @Mock private GenericRepository<DepartmentModel, String> genericRepository;

  @Mock private ReactiveMongoTemplate reactiveMongoTemplate;

  @InjectMocks private DepartmentService departmentService;

  private DepartmentModel departmentModel;

  @BeforeEach
  public void setUp() {
    departmentModel =
        new DepartmentModel(
            UUID.randomUUID().toString(), 1, "test", DateUtil.now(), DateUtil.now());
  }

  @Test
  void createDepartmentTest() {
    Mockito.when(genericRepository.save(Mockito.any())).thenReturn(Mono.just(departmentModel));
    StepVerifier.create(departmentService.createDepartment(departmentModel))
        .expectNext(departmentModel)
        .verifyComplete();
  }

  @Test
  void createDepartmentTestException() {
    Mockito.when(genericRepository.save(Mockito.any()))
        .thenReturn(Mono.error(new Exception("Error")));
    StepVerifier.create(departmentService.createDepartment(departmentModel))
        .expectError(MongoException.class)
        .log()
        .verify();
  }

  @Test
  void getDepartmentTest() {
    Mockito.when(genericRepository.findById(departmentModel.getId()))
        .thenReturn(Mono.just(departmentModel));
    StepVerifier.create(departmentService.getDepartment(departmentModel.getId()))
        .expectNext(departmentModel)
        .verifyComplete();
  }

  @Test
  void getDepartmentTestException() {
    Mockito.when(genericRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.error(new MongoException("Error")));
    StepVerifier.create(departmentService.getDepartment(departmentModel.getId()))
        .expectError(MongoException.class)
        .log()
        .verify();
  }

  @SuppressWarnings("unchecked")
  @Test
  void getAllDepartmentTest() {
    Mockito.when(reactiveMongoTemplate.find(Mockito.any(Query.class), Mockito.any(Class.class)))
        .thenReturn(Flux.just(departmentModel, new DepartmentModel(2)));
    Mockito.when(reactiveMongoTemplate.count(Mockito.any(Query.class), Mockito.any(Class.class)))
        .thenReturn(Mono.just(2L));
    StepVerifier.create(departmentService.getAllDepartment(PageRequest.of(0, 5)))
        .thenConsumeWhile(
            page -> {
              Assertions.assertEquals(2, page.getTotalElements());
              Assertions.assertEquals(departmentModel, page.getContent().get(0));
              Assertions.assertEquals(2, page.getContent().get(1).getDepartmentNo());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void deleteDepartmentTest() {
    Mockito.when(genericRepository.existsById(Mockito.any(String.class)))
        .thenReturn(Mono.just(Boolean.TRUE));
    Mockito.when(genericRepository.deleteById(Mockito.any(String.class))).thenReturn(Mono.empty());
    StepVerifier.create(departmentService.deleteDepartment(Mockito.anyString()))
        .assertNext(jsonObject -> Assertions.assertEquals("ok", jsonObject.getAsString("deleted")))
        .verifyComplete();
  }

  @Test
  void deleteDepartmentTestException() {
    Mockito.when(genericRepository.existsById(Mockito.any(String.class)))
        .thenReturn(Mono.just(Boolean.TRUE));
    StepVerifier.create(departmentService.deleteDepartment(Mockito.anyString()))
        .expectError(MongoException.class)
        .log()
        .verify();
  }
}
