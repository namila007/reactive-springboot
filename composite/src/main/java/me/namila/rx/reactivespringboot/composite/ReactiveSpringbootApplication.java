package me.namila.rx.reactivespringboot.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.namila.rx.reactivespringboot"})
public class ReactiveSpringbootApplication {
  public static void main(String[] args) {
    SpringApplication.run(ReactiveSpringbootApplication.class, args);
  }
}
