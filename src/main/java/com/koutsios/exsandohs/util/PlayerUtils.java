package com.koutsios.exsandohs.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Random;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PlayerUtils {

  private static Random random = new Random();

  public static String generateName(List<String> names) {
    int index = random.nextInt(names.size());
    return names.get(index);
  }
}
