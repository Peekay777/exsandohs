package com.koutsios.exsandohs.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "player")
@Getter
@Setter
public class PlayerProperties {
  private List<String> names;
}
