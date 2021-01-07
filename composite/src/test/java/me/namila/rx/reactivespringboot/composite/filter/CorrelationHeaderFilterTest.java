package me.namila.rx.reactivespringboot.composite.filter;

import me.namila.rx.reactivespringboot.composite.controller.TestWebClientController;
import me.namila.rx.reactivespringboot.core.constant.BasicConstants;
import me.namila.rx.reactivespringboot.core.constant.Routes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/** The type Correlation header filter test. */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TestWebClientController.class)
public class CorrelationHeaderFilterTest {

  @Autowired private WebTestClient webClient;

  /** Test correlation id on body. */
  @Test
  void testCorrelationIdOnBody() {
    String correlationId = "ABCD";
    webClient
        .get()
        .uri(Routes.BASE_END_POINT + "/test")
        .header(BasicConstants.WebHeader.X_HEADER_PREFIX+BasicConstants.WebHeader.CORRELATION_HEADER, correlationId)
        .exchange()
        .expectHeader()
        .value(
            BasicConstants.WebHeader.X_HEADER_PREFIX+BasicConstants.WebHeader.CORRELATION_HEADER,
            x -> {
              Assertions.assertEquals(correlationId, x);
            })
        .expectBody()
        .jsonPath("$.first")
        .isEqualTo(correlationId)
        .jsonPath("$.second")
        .isEqualTo(correlationId);
  }

  /** Test correlation id on header. */
  @Test
  void testCorrelationIdOnHeader() {
    String correlationId = "ABCDEFG";
    webClient
        .get()
        .uri(Routes.BASE_END_POINT + "/test")
        .header(BasicConstants.WebHeader.X_HEADER_PREFIX+BasicConstants.WebHeader.CORRELATION_HEADER, correlationId)
        .exchange()
        .expectHeader()
        .value(
                BasicConstants.WebHeader.X_HEADER_PREFIX+BasicConstants.WebHeader.CORRELATION_HEADER,
            x -> {
              Assertions.assertEquals(correlationId, x);
            });
  }
}
