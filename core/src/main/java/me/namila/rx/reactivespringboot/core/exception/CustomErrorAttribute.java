package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * The type Custom error attribute.
 */
@Component
public class CustomErrorAttribute extends DefaultErrorAttributes {
  @Override
  public Map<String, Object> getErrorAttributes(
          ServerRequest request, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
    Throwable error = getError(request);
    errorAttributes.put("exception", error.getClass().getName());
    errorAttributes.put("message", determineMessage(error));
    errorAttributes.put("debugMessage", determineDebugMessage(error));
    return errorAttributes;
  }

  private String determineDebugMessage(Throwable error) {
    if (error instanceof ResponseStatusException) {
      return ((ResponseStatusException) error).getReason();
    }
    return (error.getMessage() != null) ? error.getMessage() : "";
  }

  private String determineMessage(Throwable error) {
    if (error instanceof AbstractException) {
      return ((AbstractException) error).getMessage();
    }
    return (error.getMessage() != null) ? error.getMessage() : "";
  }
}
