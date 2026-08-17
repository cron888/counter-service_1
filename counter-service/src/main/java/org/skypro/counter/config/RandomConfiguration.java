package org.skypro.counter.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Random;

@Configuration
public class RandomConfiguration {

   @Value("${app.configuration.non-random-constant}")
   private boolean nonRandomConstant;

   /**
    * Реализация по умолчанию, инжектится когда не указаны квалифаеры.
    * Позволяет рандомизировать поведение.
    */
   @Bean
   @Primary
   public Random defaultInstance() {
      return new Random();
   }

   /**
    * Дополнительная реализация, используется при указания квалифаера constantInstance.
    * Часто при тестировании случайность мешает, и на время тестирования можно поставить всегда одинаковое поведение.
    */
   @Bean
   @Qualifier("constantInstance")
   public Random constantInstance() {
      /*
       * Вспоминаем ООП - перед нами анонимный класс с переопределением поведения метода nextBoolean.
       * теперь этот метод всегда возвращает ожидаемое значение, которое можно указать в application.properties
       */
      return new Random() {
         @Override
         public boolean nextBoolean() {
            return nonRandomConstant;
         }
      };
   }
}