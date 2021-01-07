package me.namila.rx.reactivespringboot.core.util;

import lombok.NonNull;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.CONTEXT_MAP;

/** Context util to get Context from Mono/Flux chain. Context stores all the "X-" http headers on a map
 * and by providing contextView and a Key it will return relevant value of the given header */
public class ContextUtil {

  private ContextUtil() {}

  /**
   * Gets header.
   *
   * @param contextView the context view
   * @param header http header
   * @return the Http header value
   */
  public static String getHeader(@NonNull ContextView contextView, @NonNull String header) {
    Optional<Map<String, String>> mapOptional = contextView.getOrEmpty(CONTEXT_MAP);
    return mapOptional
        .map(stringStringMap -> stringStringMap.get(header.toUpperCase()))
        .orElse(null);
  }

  /**
   * Add New X- Http header.
   *
   * @param contextView the context view
   * @param header http-header
   * @param value value
   */
  public static void addHeader(
      @NonNull ContextView contextView, @NonNull String header, @NonNull String value) {
    Optional<Map<String, String>> mapOptional = contextView.getOrEmpty(CONTEXT_MAP);
    mapOptional.ifPresent(stringStringMap -> stringStringMap.put(header.toUpperCase(), value));
  }

  /**
   * Add Http-header functional function.
   *
   * @param header the header
   * @param value the value
   * @return the function
   */
  public static Function<ContextView, Mono<ContextView>> addHeaderFunctional(
      @NonNull String header, @NonNull String value) {
    return context -> {
      Optional<Map<String, String>> mapOptional = context.getOrEmpty(CONTEXT_MAP);
      mapOptional.ifPresent(stringStringMap -> stringStringMap.put(header.toUpperCase(), value));
      return Mono.just(context);
    };
  }
}
