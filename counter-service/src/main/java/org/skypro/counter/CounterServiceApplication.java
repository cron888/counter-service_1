package org.skypro.counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Запустите приложение и посмотрите на поясняющие комментарии в org.skypro.counter.controller.CounterController
@SpringBootApplication
public class CounterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounterServiceApplication.class, args);
    }

}