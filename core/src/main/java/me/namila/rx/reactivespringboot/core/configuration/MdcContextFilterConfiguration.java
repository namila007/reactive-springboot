package me.namila.rx.reactivespringboot.core.configuration;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;

import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.CONTEXT_MAP;

/** The type Mdc context filter configuration.
 * This will add Current Context of the Mono/Flux to the MDC. Logger will print a [%CORRELATION-ID] to track the
 * relevant request*/
@Configuration
public class MdcContextFilterConfiguration {

  /** The Logger. */
  Logger LOGGER = LoggerFactory.getLogger(MdcContextFilterConfiguration.class);

  /** Post hook. */
  @PostConstruct
  void postHook() {
    LOGGER.debug("Calling post Hook");
    Hooks.onEachOperator(CONTEXT_MAP, Operators.lift((x, y) -> new MdcContextLifter(y)));
    LOGGER.debug("End post Hook");
  }

  /** Cleanup hook. */
  @PreDestroy
  void cleanupHook() {
    LOGGER.debug("Calling preDestroy Hook");
    Hooks.resetOnEachOperator(CONTEXT_MAP);
    LOGGER.debug("End preDestroy Hook");
  }

  private void CopyToMdc(Context context) {
    LOGGER.debug("Context {}", context.toString());
    if (context.isEmpty()) {
      MDC.clear();
      return;
    }
    Optional<Map<String, String>> mapOptional = context.getOrEmpty(CONTEXT_MAP);
    mapOptional.ifPresent(MDC::setContextMap);
    LOGGER.debug("MDC {}", MDC.getCopyOfContextMap());
  }

  /**
   * The type Mdc context lifter.
   *
   * @param <T> the type parameter
   */
  class MdcContextLifter<T> implements CoreSubscriber<T> {
    private CoreSubscriber<? super T> coreSubscriber;

    /**
     * Instantiates a new Mdc context lifter.
     *
     * @param coreSubscriber the core subscriber
     */
    MdcContextLifter(CoreSubscriber<? super T> coreSubscriber) {
      this.coreSubscriber = coreSubscriber;
    }

    @Override
    public Context currentContext() {
      return coreSubscriber.currentContext();
    }

    @Override
    public void onSubscribe(Subscription s) {
      coreSubscriber.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
      CopyToMdc(coreSubscriber.currentContext());
      coreSubscriber.onNext(t);
    }

    @Override
    public void onError(Throwable t) {
      coreSubscriber.onError(t);
    }

    @Override
    public void onComplete() {
      coreSubscriber.onComplete();
    }
  }
}
