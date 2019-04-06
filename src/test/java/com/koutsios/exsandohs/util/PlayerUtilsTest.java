package com.koutsios.exsandohs.util;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class PlayerUtilsTest {

  @Test
  public void generateName_givenAListOfNames_when_generateName_then_returnNameInList() {

    List<String> names = Arrays.asList("Damon","Dick","Arturo");

    String name = PlayerUtils.generateName(names);

    assertThat(names, hasItem(name));
  }
}