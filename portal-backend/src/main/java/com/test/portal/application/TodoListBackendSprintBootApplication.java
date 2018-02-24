package com.test.portal.application;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.test.portal")
@Log4j2
public class TodoListBackendSprintBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(TodoListBackendSprintBootApplication.class, args);
  }
}
