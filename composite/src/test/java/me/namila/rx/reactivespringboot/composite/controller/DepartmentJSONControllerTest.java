package me.namila.rx.reactivespringboot.composite.controller;


import me.namila.rx.reactivespringboot.core.constant.BasicConstants;
import me.namila.rx.reactivespringboot.core.constant.Routes;
import me.namila.rx.reactivespringboot.core.exception.MongoException;
import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import me.namila.rx.reactivespringboot.core.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentJSONControllerTest {

  private static final String ENDPOINT = BasicConstants.Symbols.FORWARD_SLASH + Routes.BASE_END_POINT + Routes.DEPARTMENT_BASEURL;

  //  private WebClient webClient;
  @LocalServerPort
  private int port;
  @MockBean
  private DepartmentRepository departmentRepository;

  private DepartmentModel departmentData;

  @InjectMocks
  private DepartmentController departmentController;


  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
//    webClient = WebClient.create("http://localhost:"+port);
    departmentData = new DepartmentModel();
    departmentData.setName("test");
    departmentData.setDepartmentNo(1);
  }

  @Test
  public void createDepartmentTest() {
    ResponseEntity<DepartmentModel> testResponse =
            new ResponseEntity<>(departmentData, HttpStatus.CREATED);

    Mockito.when(departmentRepository.save(Mockito.any())).thenReturn(Mono.just(departmentData));
    Mono<ResponseEntity<DepartmentModel>> response =
            departmentController.createDepartment(departmentData);
    StepVerifier.create(response).expectNext(testResponse).expectComplete();

//    webClient.post().uri(ENDPOINT).body(departmentData)
  }

  @Test
  public void createDepartmentTestMongoException() {
    Mockito.when(departmentRepository.save(Mockito.any())).thenReturn(Mono.error(new Exception("Test")));
    Mono<ResponseEntity<DepartmentModel>> response =
            departmentController.createDepartment(departmentData);
    StepVerifier.create(response)
            .expectError(MongoException.class)
            .verify();
  }
}
