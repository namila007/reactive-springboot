package me.namila.rx.reactivespringboot.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.WebHeader.CORRELATION_HEADER;

/** The Correlation header filter to extract X-Correlation-ID header and add to webflux context and writing it
 * to response. */
@Component
@Order(1) // executed in first
public class CorrelationHeaderFilter implements WebFilter {
  /** The Logger. */
  Logger LOGGER = LoggerFactory.getLogger(CorrelationHeaderFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    Optional<List<String>> correlationHeader =
        Optional.ofNullable(exchange.getRequest().getHeaders().get(CORRELATION_HEADER)); //extracting header
    LOGGER.info("Correlation ID: {}", correlationHeader);
    correlationHeader.ifPresent(
        id -> {
          exchange.getResponse().getHeaders().set(CORRELATION_HEADER, id.get(0)); //setting to response headers
        });
    return chain
        .filter(exchange)
        .contextWrite(
            context -> //writing to context key=X-Correlation-ID
                context.put(
                    CORRELATION_HEADER,
                    correlationHeader.isPresent() ? correlationHeader.get().get(0) : ""));
  }
}
