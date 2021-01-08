package me.namila.rx.reactivespringboot.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.CONTEXT_MAP;
import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.WebHeader.CORRELATION_HEADER;
import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.WebHeader.X_HEADER_PREFIX;

/**
 * The Correlation header filter to extract X-Correlation-ID header and add to webflux context and
 * writing it to response.
 * All the X- headers are stored in Upper cases;
 */
@Component
@Order(1) // executed in first
public class CorrelationHeaderFilter implements WebFilter {
  /** The Logger. */
  Logger LOGGER = LoggerFactory.getLogger(CorrelationHeaderFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    exchange.getResponse().beforeCommit(()->addContextToResponse(exchange.getResponse()));

    return chain
        .filter(exchange)
            .contextWrite(
            context -> // writing to context key=contextMap
            addHttpHeaderToContext(exchange.getRequest(), context)
            );
  }

  private Context addHttpHeaderToContext(
      final ServerHttpRequest serverHttpRequest, final Context context) {
    final Map<String, String> contextMap =
        serverHttpRequest.getHeaders().toSingleValueMap().entrySet().stream()
            .filter(x -> x.getKey().toUpperCase().startsWith(X_HEADER_PREFIX))
            .collect(
                Collectors.toMap(
                    key -> key.getKey().substring(X_HEADER_PREFIX.length()).toUpperCase(), Map.Entry::getValue));
    LOGGER.debug("Request X-Headers: {}", contextMap.entrySet().toString());
    return context.put(CONTEXT_MAP, contextMap);
  }

  private Mono<Void> addContextToResponse(final ServerHttpResponse response) {
      return Mono.deferContextual(contextView ->
              Mono.just(contextView.getOrEmpty(CONTEXT_MAP)))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .map(x->{
                final HttpHeaders headers = response.getHeaders();
                ((Map)(x)).forEach((key,val)-> {
                    headers.add((X_HEADER_PREFIX+key.toString()).toUpperCase(),val.toString());
                });
                return x;
              }).then();
  }

}
