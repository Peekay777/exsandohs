package com.koutsios.exsandohs.configuration;

import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public Random random() {
    return new Random();
  }
}
