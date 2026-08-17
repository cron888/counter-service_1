package org.skypro.counter.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HelloService {
   private final Random random;

   public HelloService(@Qualifier("constantInstance") Random random) {
      this.random = random;
   }

   public String randomHello() {
      if (random.nextBoolean()) {
         return "Hello, World!";
      } else {
         return "Ciao, mondo";
      }
   }
}