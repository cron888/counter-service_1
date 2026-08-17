package org.skypro.counter.controller;

import org.skypro.counter.dto.UserRequestCounterDto;
import org.skypro.counter.service.CounterService;
import org.skypro.counter.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
   private final CounterService counterService;
   private final HelloService helloService;

   public CounterController(CounterService counterService, HelloService helloService) {
      this.counterService = counterService;
      this.helloService = helloService;
   }

   /*
    1. Попробуйте поменять настройку app.configuration.non-random-constant, перезапустить приложение и посмотреть,
    что выдает http://localhost:8081/myapplication/hello при нескольких запросах подряд при значении true и false.
    Наш переопределенный метод nextBoolean из org.skypro.counter.config.RandomConfiguration.constantInstance всегда выдает стабильное констентное значение.
    2. Уберите @Qualifier("constantInstance") в конструкторе org.skypro.counter.service.HelloService#HelloService(java.util.Random)
    и посмотрите, что выдает http://localhost:8081/myapplication/hello при нескольких запросах подряд.
    Без квалифаеров бин, помеченный @Primary - org.skypro.counter.config.RandomConfiguration.defaultInstance предоставляет
    рандомизированное поведение и выдает случайные значения true или false.
    */
   @GetMapping("/hello")
   public String hello() {
      return helloService.randomHello();
   }

   /*
    1. Откройте анонимную и обычную вкладку, и посмотрите что возвращает несколько вызовов
    http://localhost:8081/myapplication/counter в каждой из вкладок. Из-за различных сессий
    @SessionScope бин UserRequestCounter ведет отдельный подсчет для каждого окна.
    2. Уберите @SessionScope над org.skypro.counter.domain.UserRequestCounter и удостоверьтесь,
    что подсчет для анонимного и обычного окна ведется общий.
    3. Уберите метод org.skypro.counter.dto.UserRequestCounterDto.getRequestCount и убедитесь,
    что получаете ошибку 406 (в логах будет HttpMediaTypeNotAcceptableException) - для классов,
    возвращаемых из контроллеров, обязательно нужны геттеры.
   */
   @GetMapping("/counter")
   public UserRequestCounterDto count() {
      counterService.increment();
      /*
       Считается хорошей практикой использовать отдельные классы для полченияи возвращения объектов в контроллерах -
       не те же самые, что используются в сервисах.
      */
       return new UserRequestCounterDto(counterService.getCount());
   }
}