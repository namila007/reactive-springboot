package me.namila.rx.reactivespringboot.composite.controller;

import me.namila.rx.reactivespringboot.core.constant.Routes;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static me.namila.rx.reactivespringboot.core.constant.BasicConstants.WebHeader.CORRELATION_HEADER;

/** The type Test web client controller to test Correlation ID. */
@RestController
@RequestMapping(value = Routes.BASE_END_POINT + "/test")
public class TestWebClientController {

  /** The Logger. */
  Logger LOGGER = LoggerFactory.getLogger(TestWebClientController.class);

  /**
   * Echo get mono.
   *
   * @return the mono
   */
  @GetMapping
  public Mono<JSONObject> echoGet() {
    return Mono.just("OK")
            .doOnEach(x->LOGGER.info("Thread {}, Value {}",Thread.currentThread().getName(),x))
        .flatMap(
            x ->
                Mono.deferContextual( //accessing context as VIEW
                        contextView -> {
                          JSONObject jsonObject = new JSONObject();
                          jsonObject.appendField(
                              "first", contextView.getOrEmpty(CORRELATION_HEADER));
                          return Mono.just(jsonObject);
                        })
                    .subscribeOn(Schedulers.immediate())
                    .publishOn(Schedulers.immediate()))
        .flatMap(
            x ->
                Mono.deferContextual(
                    contextView -> {
                        JSONObject jsonObject = new JSONObject(x);
                        jsonObject.appendField(
                                "second", contextView.getOrEmpty(CORRELATION_HEADER));
                        return Mono.just(jsonObject);
                    }));
  }
}
